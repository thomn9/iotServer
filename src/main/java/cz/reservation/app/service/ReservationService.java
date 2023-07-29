package cz.reservation.app.service;


import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;

public interface ReservationService {
    void createReservableSchedule(Long serviceDefinitionId);

    ReservationDetailDto createReservation(ReservationBaseDto reservationBaseDto);

    ReservationDetailDto getReservationDetail(String reservationCode);

    void deleteReservation(String reservationCode);

}
