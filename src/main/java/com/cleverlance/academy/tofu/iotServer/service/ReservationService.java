package com.cleverlance.academy.tofu.iotServer.service;


import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationBaseDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDetailDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservableTimeWindowForADateDto;

import java.util.List;

public interface ReservationService {
    List<ReservableTimeWindowForADateDto> getReservableTimeWindowsSchedule(Long durationOfReservableTimeWindowId);

    ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto);

    ReservationDetailDto getReservationDetail(String reservationCode);

    void deleteReservation(String reservationCode);

}
