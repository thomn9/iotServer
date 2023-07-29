package cz.reservation.app.service;

import cz.reservation.app.model.RangeOfLocalTimeFactory;
import cz.reservation.app.model.entity.Reservation;
import cz.reservation.app.repository.JpaDurationOfReservableTimeWindowsRepository;
import cz.reservation.app.repository.JpaReservationRepository;
import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.ReservableTimeWindowForADateDto;
import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private AdministrationService administrationService;

    @Autowired
    private JpaDurationOfReservableTimeWindowsRepository durationOfReservableTimeWindowsRepository;

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<ReservableTimeWindowForADateDto> getReservableTimeWindowsSchedule(Long durationOfReservableTimeWindowId) {
        LocalDate today = LocalDate.now();

        List<LocalDate> reservableDatesSchedule = today.datesUntil(today.plusDays(30)).collect(Collectors.toList());

        List<Reservation> reservationDtos = reservationRepository.findByReservationDateBetween(today,reservableDatesSchedule.get(reservableDatesSchedule.size()-1));

        List<BusinessHoursDto> businessHoursDtos = administrationService.getBusinessHours();

        Duration duration = durationOfReservableTimeWindowsRepository
                .findById(durationOfReservableTimeWindowId)
                .get().getDuration();

        return reservableDatesSchedule
                .stream()
                .map(date -> {

                            List<Reservation> applicableReservationDtos = reservationDtos
                                    .stream()
                                    .filter(reservation -> reservation.getReservationDate().equals(date))
                                    .collect(Collectors.toList());

                            List<BusinessHoursDto> applicableBusinessHoursDtos = businessHoursDtos
                                    .stream()
                                    .filter(businessHoursDto -> businessHoursDto.getDayOfWeek().equals(DayOfWeek.from(date)))
                                    .collect(Collectors.toList());

                            List<Range<LocalTime>> reservableTimeWindows = getReservableTimeWindowsForDayOfWeek(duration, applicableBusinessHoursDtos, applicableReservationDtos);

                            return ReservableTimeWindowForADateDto.builder().date(date).reservableTimeWindows(reservableTimeWindows).build();}
                )
                .collect(Collectors.toList());
    }

   private List<Range<LocalTime>> getReservableTimeWindowsForDayOfWeek(Duration duration, List<BusinessHoursDto> applicableBusinessHoursDtos, List<Reservation> applicableReservations) {

        List<Range<LocalTime>> reservableTimeWindowsForDayOfWeek = new ArrayList<>();

        List<ReservationDetailDto> reservationDetailDtos = applicableReservations
                .stream()
                .map(reservation -> conversionService.convert(reservation,ReservationDetailDto.class))
                .collect(Collectors.toList());

        applicableBusinessHoursDtos.forEach(applicableBusinessHoursDto -> {
            Range<LocalTime> businessHoursTimeRange = applicableBusinessHoursDto.getBusinessHoursTimeRange();
            LocalTime openingTime = applicableBusinessHoursDto.getBusinessHoursTimeRange().getMinimum();
            Range<LocalTime> reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(openingTime,openingTime.plus(duration.toMinutes(), ChronoUnit.MINUTES));

            while (businessHoursTimeRange.containsRange(reservableTimeWindow)) {
                Range<LocalTime> finalReservableTimeWindow = reservableTimeWindow;
                List<ReservationDetailDto> overlappingReservations = reservationDetailDtos
                        .stream()
                        .filter(reservationDetailDto -> finalReservableTimeWindow.isOverlappedBy(reservationDetailDto.getReservationTimeRange()))
                        .collect(Collectors.toList());
                long countOfNonZeroIntersectionOverlappingReservations = overlappingReservations
                        .stream()
                        .filter(reservationDetailDto -> !reservationDetailDto.getReservationTimeRange().isStartedBy(finalReservableTimeWindow.getMaximum()) && !reservationDetailDto.getReservationTimeRange().isEndedBy(finalReservableTimeWindow.getMinimum()))
                        .count();
                if(overlappingReservations.isEmpty() || countOfNonZeroIntersectionOverlappingReservations == 0L){
                    reservableTimeWindowsForDayOfWeek.add(reservableTimeWindow);
                    LocalTime nextStartTimeOfReservableTimeWindow = reservableTimeWindow.getMaximum();
                    reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(nextStartTimeOfReservableTimeWindow,nextStartTimeOfReservableTimeWindow.plus(duration.toMinutes(), ChronoUnit.MINUTES));
                } else {
                    LocalTime nextStartTimeOfReservableTimeWindow = overlappingReservations
                            .stream()
                            .max((reservationDetailDtoA,reservationDetailDtoB) ->
                                    reservationDetailDtoA.getReservationTimeRange().getComparator().compare(reservationDetailDtoA.getReservationTimeRange().getMaximum(), reservationDetailDtoB.getReservationTimeRange().getMaximum()))
                            .get()
                            .getReservationTimeRange()
                            .getMaximum();
                    reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(nextStartTimeOfReservableTimeWindow,nextStartTimeOfReservableTimeWindow.plus(duration.toMinutes(), ChronoUnit.MINUTES));
                }
            }
        });

        return reservableTimeWindowsForDayOfWeek;
    }

    @Transactional
    @Override
    public ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto) {
        //todo must match reservable time window (duration) and businness hours + date
        List<Reservation> existingReservationsForGivenDate = reservationRepository.findByReservationDate(reservationBaseDto.getReservationDate());
        List<ReservationDetailDto> reservationDetailDtos = existingReservationsForGivenDate
                .stream()
                .map(reservation -> conversionService.convert(reservation,ReservationDetailDto.class))
                .collect(Collectors.toList());
        List<ReservationDetailDto> overlappingReservations = reservationDetailDtos
                .stream()
                .filter(reservationDetailDto -> reservationBaseDto.getReservationTimeRange().isOverlappedBy(reservationDetailDto.getReservationTimeRange()))
                .collect(Collectors.toList());
        long countOfNonZeroIntersectionOverlappingReservations = overlappingReservations
                .stream()
                .filter(reservationDetailDto -> !reservationDetailDto.getReservationTimeRange().isStartedBy(reservationBaseDto.getReservationTimeRange().getMaximum()) && !reservationDetailDto.getReservationTimeRange().isEndedBy(reservationBaseDto.getReservationTimeRange().getMinimum()))
                .count();
        if (countOfNonZeroIntersectionOverlappingReservations != 0) {
            //todo how to propagate errors
            throw new Error();
        }
        Reservation reservation = conversionService.convert(reservationBaseDto, Reservation.class);
        reservation.setReservationCode(UUID.randomUUID().toString());
        return conversionService.convert(reservationRepository.save(reservation),ReservationDetailDto.class);
    }

    @Override
    public ReservationDetailDto getReservationDetail(String reservationCode) {
        return conversionService.convert(reservationRepository.getByReservationCode(reservationCode),ReservationDetailDto.class);
    }

    @Transactional
    @Override
    public void deleteReservation(String reservationCode) {
        reservationRepository.deleteByReservationCode(reservationCode);
    }

}
