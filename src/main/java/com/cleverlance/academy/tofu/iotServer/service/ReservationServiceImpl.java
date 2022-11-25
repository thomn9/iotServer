package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.dto.*;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservation;
import com.cleverlance.academy.tofu.iotServer.repository.JpaDurationOfReservableTimeWindowsRepository;
import com.cleverlance.academy.tofu.iotServer.repository.JpaReservationRepository;
import com.cleverlance.academy.tofu.iotServer.service.mapper.ReservationMapper;
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
        List<LocalDate> reservationDatesList = today.datesUntil(today.plusDays(30)).collect(Collectors.toList());
        List<BusinessHoursDto> businessHoursDtos = administrationService.getBusinessHours();
        Duration duration = durationOfReservableTimeWindowsRepository
                .getById(durationOfReservableTimeWindowId)
                .getDuration();

        //todo return from DB, this input must be evaluted during creation of windows (i.e. curently in method getReservableTimeWindowsForDayOfWeek()) not afterwards
        List<Reservation> currentReservations = List.of(Reservation.builder().reservationDate(LocalDate.of(2022, 11, 23)).reservationStartTime(LocalTime.of(8, 0)).reservationEndTime(LocalTime.of(8, 45)).build());

        return reservationDatesList
                .stream()
                .map(date -> {

                            List<BusinessHoursDto> applicableBusinessHoursDtos = businessHoursDtos
                                    .stream()
                                    .filter(businessHoursDto -> businessHoursDto.getDayOfWeek().equals(DayOfWeek.from(date)))
                                    .collect(Collectors.toList());

                            List<ReservableTimeWindowWithOccupancyInfoDto> reservableTimeWindowWithOccupancyInfoDtoList = getReservableTimeWindowsForDayOfWeek(duration, applicableBusinessHoursDtos)
                                    .stream()
                                    .peek(reservableTimeWindowWithOccupancyInfoDto -> {
                                        if (currentReservations.stream().anyMatch(currentReservation -> currentReservation.getReservationDate().equals(date) && currentReservation.getReservationStartTime().equals(reservableTimeWindowWithOccupancyInfoDto.getReservationStartTime()) && currentReservation.getReservationEndTime().equals(reservableTimeWindowWithOccupancyInfoDto.getReservationEndTime()))) {
                                            reservableTimeWindowWithOccupancyInfoDto.setOccupied(true);
                                        }
                                    })
                                    .collect(Collectors.toList());

                            return ReservationDataForADateDto.builder().date(date).reservableTimeWindowWithOccupancyInfoDtoList(reservableTimeWindowWithOccupancyInfoDtoList).build();}
                )
                .collect(Collectors.toList());
    }

    private List<ReservableTimeWindowWithOccupancyInfoDto> getReservableTimeWindowsForDayOfWeek(Duration duration, List<BusinessHoursDto> applicableBusinessHoursDtos) {

        List<ReservableTimeWindowWithOccupancyInfoDto> reservableTimeWindowsForDayOfWeek = new ArrayList<>();

        applicableBusinessHoursDtos.forEach(applicableBusinessHoursDto -> {
            LocalTime openingTime = applicableBusinessHoursDto.getOpeningTime();
            LocalTime closingTime = applicableBusinessHoursDto.getClosingTime();
            LocalTime computationTime = openingTime;
            while (closingTime.isAfter(computationTime)) {
                LocalTime adjustedComputationTime = computationTime.plus(duration.toMinutes(), ChronoUnit.MINUTES);
                if (adjustedComputationTime.isBefore(closingTime) || adjustedComputationTime.equals(closingTime)) {
                    reservableTimeWindowsForDayOfWeek.add(ReservableTimeWindowWithOccupancyInfoDto.builder().reservationStartTime(computationTime).reservationEndTime(adjustedComputationTime).build());
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
