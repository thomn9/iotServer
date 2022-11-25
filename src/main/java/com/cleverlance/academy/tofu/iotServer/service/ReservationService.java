package com.cleverlance.academy.tofu.iotServer.service;


import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataDetailDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataForADateDto;

import java.util.List;

public interface ReservationService {
    List<ReservationDataForADateDto> getReservableTimeWindowsSchedule(Long durationOfReservableTimeWindowId);

    ReservationDataDetailDto createReservation(ReservationDataDto reservationDataDto);

    ReservationDataDetailDto getReservationDetail(String reservationCode);

    void deleteReservation(String reservationCode);

}
