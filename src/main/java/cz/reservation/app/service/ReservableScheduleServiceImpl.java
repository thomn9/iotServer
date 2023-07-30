package cz.reservation.app.service;

import cz.reservation.app.model.RangeOfLocalTimeFactory;
import cz.reservation.app.model.ReservableState;
import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import cz.reservation.app.repository.JpaReservableScheduleRepository;
import cz.reservation.app.repository.JpaServiceDefinitionRepository;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservableScheduleServiceImpl implements ReservableScheduleService {

    @Autowired
    private AdministrationService administrationService;

    @Autowired
    private JpaServiceDefinitionRepository serviceDefinitionRepository;

    @Autowired
    private JpaReservableScheduleRepository reservableScheduleRepository;

    @Autowired
    private ConversionService conversionService;

    @Override
    public void createReservableSchedule(Long serviceDefinitionId) {
        LocalDate today = LocalDate.now();

        List<LocalDate> reservableDatesSchedule = today.datesUntil(today.plusDays(30)).collect(Collectors.toList());

        List<BusinessHoursDto> businessHoursDtos = administrationService.getBusinessHours();

        Duration duration = serviceDefinitionRepository
                .findById(serviceDefinitionId)
                .get().getDuration();

        List<ReservableScheduleBaseDto> reservableScheduleBaseDtoList = new ArrayList<>();

        reservableDatesSchedule.forEach(date -> {

            List<BusinessHoursDto> applicableBusinessHoursDtos = businessHoursDtos
                    .stream()
                    .filter(businessHoursDto -> businessHoursDto.getDayOfWeek().equals(DayOfWeek.from(date)))
                    .collect(Collectors.toList());

            List<Range<LocalTime>> reservableScheduleForDayOfWeek = createReservableScheduleForDayOfWeek(duration, applicableBusinessHoursDtos);

            reservableScheduleForDayOfWeek.forEach(timeRange -> {
                reservableScheduleBaseDtoList.add(ReservableScheduleBaseDto.builder()
                        .reservationDate(date)
                        .reservableState(ReservableState.AVAILABLE)
                        .reservableTimeWindow(timeRange)
                        .serviceDefinitionId(serviceDefinitionId)
                        .build());
            });
        });



        reservableScheduleRepository.saveAll(
                reservableScheduleBaseDtoList.stream()
                        .map(reservableScheduleBaseDto -> conversionService.convert(reservableScheduleBaseDto, ReservableSchedule.class))
                        .collect(Collectors.toList())
        );
    }

    private List<Range<LocalTime>> createReservableScheduleForDayOfWeek(Duration duration, List<BusinessHoursDto> applicableBusinessHoursDtos) {

        List<Range<LocalTime>> reservableTimeWindowsForDayOfWeek = new ArrayList<>();

        applicableBusinessHoursDtos.forEach(applicableBusinessHoursDto -> {
            Range<LocalTime> businessHoursTimeRange = applicableBusinessHoursDto.getBusinessHoursTimeRange();
            LocalTime openingTime = applicableBusinessHoursDto.getBusinessHoursTimeRange().getMinimum();
            Range<LocalTime> reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(openingTime,openingTime.plus(duration.toMinutes(), ChronoUnit.MINUTES));

            while (businessHoursTimeRange.containsRange(reservableTimeWindow)) {
                reservableTimeWindowsForDayOfWeek.add(reservableTimeWindow);
                LocalTime nextStartTimeOfReservableTimeWindow = reservableTimeWindow.getMaximum();
                reservableTimeWindow = RangeOfLocalTimeFactory.getNewRangeOfLocalTime(nextStartTimeOfReservableTimeWindow,nextStartTimeOfReservableTimeWindow.plus(duration.toMinutes(), ChronoUnit.MINUTES));
            }
        });

        return reservableTimeWindowsForDayOfWeek;
    }

    @Override
    public List<ReservableScheduleBaseDto> getReservableSchedule() {
        return reservableScheduleRepository.findAll()
                .stream()
                .map(reservableSchedule -> conversionService.convert(reservableSchedule,ReservableScheduleBaseDto.class))
                .collect(Collectors.toList());
    }

}
