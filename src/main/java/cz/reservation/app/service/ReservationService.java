package cz.reservation.app.service;


import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.model.dto.ReservableTimeWindowForADateDto;

import java.util.List;

public interface ReservationService {
    List<ReservableTimeWindowForADateDto> getReservableTimeWindowsSchedule(Long durationOfReservableTimeWindowId);

    ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto);

    ReservationDetailDto getReservationDetail(String reservationCode);

    void deleteReservation(String reservationCode);

}
