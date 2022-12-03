package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.RangeOfLocalTimeFactory;
import com.cleverlance.academy.tofu.iotServer.model.dto.*;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservation;
import com.cleverlance.academy.tofu.iotServer.repository.JpaDurationOfReservableTimeWindowsRepository;
import com.cleverlance.academy.tofu.iotServer.repository.JpaReservationRepository;
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

        List<Reservation> reservationDataDtos = reservationRepository.findByReservationDateIsBetween(today,reservableDatesSchedule.get(reservableDatesSchedule.size()-1));

        List<BusinessHoursDto> businessHoursDtos = administrationService.getBusinessHours();

        Duration duration = durationOfReservableTimeWindowsRepository
                .findById(durationOfReservableTimeWindowId)
                .get().getDuration();

        return reservableDatesSchedule
                .stream()
                .map(date -> {

                            List<Reservation> applicableReservationDataDtos = reservationDataDtos
                                    .stream()
                                    .filter(reservation -> reservation.getReservationDate().equals(DayOfWeek.from(date)))
                                    .collect(Collectors.toList());

                            List<BusinessHoursDto> applicableBusinessHoursDtos = businessHoursDtos
                                    .stream()
                                    .filter(businessHoursDto -> businessHoursDto.getDayOfWeek().equals(DayOfWeek.from(date)))
                                    .collect(Collectors.toList());

                            List<ReservableTimeWindowDto> reservableTimeWindowDtos = getReservableTimeWindowsForDayOfWeek(duration, applicableBusinessHoursDtos, applicableReservationDataDtos);

                            return ReservableTimeWindowForADateDto.builder().date(date).reservableTimeWindowDtos(reservableTimeWindowDtos).build();}
                )
                .collect(Collectors.toList());
    }

   private List<ReservableTimeWindowDto> getReservableTimeWindowsForDayOfWeek(Duration duration, List<BusinessHoursDto> applicableBusinessHoursDtos, List<Reservation> applicableReservation) {

        List<ReservableTimeWindowDto> reservableTimeWindowsForDayOfWeek = new ArrayList<>();

        List<ReservationDetailDto> reservationDetailDtos = applicableReservation
                .stream()
                .map(reservation -> conversionService.convert(reservation,ReservationDetailDto.class))
                .collect(Collectors.toList());

        applicableBusinessHoursDtos.forEach(applicableBusinessHoursDto -> {
            //todo new
            Range<LocalTime> businessHoursTimeRange = applicableBusinessHoursDto.getBusinessHoursTimeRange();
            LocalTime openingTime = applicableBusinessHoursDto.getBusinessHoursTimeRange().getMinimum();
            Range<LocalTime> reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(openingTime,openingTime.plus(duration.toMinutes(), ChronoUnit.MINUTES));

            while (businessHoursTimeRange.containsRange(reservableTimeWindow)) {
                List<ReservationDetailDto> overlappingReservations = reservationDetailDtos
                        .stream()
                        .filter(reservationDetailDto -> reservableTimeWindow.isOverlappedBy(reservationDetailDto.getReservationTimeRange()))
                        .collect(Collectors.toList());
                if(overlappingReservations.isEmpty()){
                    reservableTimeWindowsForDayOfWeek.add(ReservableTimeWindowDto.builder().reservableTimeRange(reservableTimeWindow).build());
                    LocalTime nextStartTimeOfReservableTimeWindow = reservableTimeWindow.getMaximum();
                    reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(nextStartTimeOfReservableTimeWindow,nextStartTimeOfReservableTimeWindow.plus(duration.toMinutes(), ChronoUnit.MINUTES));
                } else {
                    overlappingReservations
                            .stream()
                            .filter(reservationDetailDto -> )
                            .count();
                    //todo odfiltrovat existingReservationTimeRange.isStartedBy(reservableTimeWindow.getMaximum())
                    // || existingReservationTimeRange.isEndedBy(reservableTimeWindow.getMinimum()))
                    //pokud by to přesto něco zůstalo, tak vytvořit instanci nového reservable okna se začátkem na maximu poslední rezervace o opakovat while

                }

                /*applicableReservationDataDtos.forEach(applicableReservationDataDto ->{
                    Range<LocalTime> existingReservationTimeRange = conversionService.convert(applicableReservationDataDto,ReservationDetailDto.class).getReservationTimeRange();
                    if (!existingReservationTimeRange.isOverlappedBy(reservableTimeWindow) ||
                        existingReservationTimeRange.isStartedBy(reservableTimeWindow.getMaximum()) ||
                        existingReservationTimeRange.isEndedBy(reservableTimeWindow.getMinimum())) {
                        //todo přidat rezervaci
                        //todo reasignovat instanci
                    } else {
                        existingReservationTimeRange.intersectionWith(reservableTimeWindow)
                        //todo reassingovat instanci
                    }});*/

            }
            //todo new

            /*LocalTime openingTime = applicableBusinessHoursDto.getBusinessHoursTimeRange().getMinimum();
            LocalTime closingTime = applicableBusinessHoursDto.getBusinessHoursTimeRange().getMaximum();
            LocalTime computationTime = openingTime;
            while (closingTime.isAfter(computationTime)) {
                LocalTime adjustedComputationTime = computationTime.plus(duration.toMinutes(), ChronoUnit.MINUTES);
                if (adjustedComputationTime.isBefore(closingTime) || adjustedComputationTime.equals(closingTime)) {
                    applicableReservationDataDtos
                            .stream()
                            //.map(reservation ->

                            .collect(Collectors.toList());

                    reservableTimeWindowsForDayOfWeek.add(ReservableTimeWindowDto.builder().reservableTimeRange(RangeOfLocalTimeFactory.getNewRangeOfLocalTime(computationTime,adjustedComputationTime)).build());
                }
                computationTime = adjustedComputationTime;
            }*/
        });

        return reservableTimeWindowsForDayOfWeek;
    }

    @Override
    public ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto) {
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
