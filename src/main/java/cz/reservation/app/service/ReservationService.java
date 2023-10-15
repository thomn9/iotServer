package cz.reservation.app.service;

import cz.reservation.app.model.dto.ReservableScheduleDto;
import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;

public interface ReservationService {

    ReservationDetailDto getReservationDetail(String reservationCode) throws Exception;

    ReservableScheduleDto lockReservableSchedule(Long reservableScheduleId) throws Exception;

    ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto) throws Exception;

    void deleteReservation(String reservationCode) throws Exception;

}
