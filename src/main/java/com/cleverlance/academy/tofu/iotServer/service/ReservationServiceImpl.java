package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.dto.*;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservation;
import com.cleverlance.academy.tofu.iotServer.repository.JpaDurationOfReservableTimeWindowsRepository;
import com.cleverlance.academy.tofu.iotServer.repository.JpaReservationRepository;
import com.cleverlance.academy.tofu.iotServer.service.mapper.ReservationMapper;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ReservationMapper reservationMapper;

    @Override
    public List<ReservationDataForADateDto> getReservableTimeWindowsSchedule(Long durationOfReservableTimeWindowId) {
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

                            return ReservationDataForADateDto.builder().date(date).reservableTimeWindowDtos(reservableTimeWindowDtos).build();}
                )
                .collect(Collectors.toList());
    }

   private List<ReservableTimeWindowDto> getReservableTimeWindowsForDayOfWeek(Duration duration, List<BusinessHoursDto> applicableBusinessHoursDtos, List<Reservation> applicableReservationDataDtos) {

        List<ReservableTimeWindowDto> reservableTimeWindowsForDayOfWeek = new ArrayList<>();

        //applicableReservationDataDtos

        applicableBusinessHoursDtos.forEach(applicableBusinessHoursDto -> {

            LocalTime openingTime = null; //applicableBusinessHoursDto.getOpeningTime();
            LocalTime closingTime = null; //applicableBusinessHoursDto.getClosingTime();
            Range<LocalTime> openingHours = Range.between(openingTime, closingTime);
            LocalTime computationTime = openingTime;
            while (closingTime.isAfter(computationTime)) {
                LocalTime adjustedComputationTime = computationTime.plus(duration.toMinutes(), ChronoUnit.MINUTES);
                if (adjustedComputationTime.isBefore(closingTime) || adjustedComputationTime.equals(closingTime)) {
                    applicableReservationDataDtos
                            .stream()
                            //.map(reservation ->

                            .collect(Collectors.toList());

                    reservableTimeWindowsForDayOfWeek.add(ReservableTimeWindowDto.builder().reservationStartTime(computationTime).reservationEndTime(adjustedComputationTime).build());
                }
                computationTime = adjustedComputationTime;
            }
        });

        return reservableTimeWindowsForDayOfWeek;
    }

    @Override
    public ReservationDataDetailDto createReservation(ReservationDataDto reservationDataDto) {
        Reservation reservation = reservationMapper.toReservationFromReservationDataDto(reservationDataDto);
        reservation.setReservationCode(UUID.randomUUID().toString());
        return reservationMapper.fromReservationToReservationDataDetailDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDataDetailDto getReservationDetail(String reservationCode) {
        return reservationMapper.fromReservationToReservationDataDetailDto(reservationRepository.getByReservationCode(reservationCode));
    }

    @Transactional
    @Override
    public void deleteReservation(String reservationCode) {
        reservationRepository.deleteByReservationCode(reservationCode);
    }

}
