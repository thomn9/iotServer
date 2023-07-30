package cz.reservation.app.service;


import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;

import java.util.List;

public interface ReservationService {

    ReservationDetailDto getReservationDetail(String reservationCode) throws Exception;

    List<ReservableScheduleBaseDto> lockReservableSchedule(Long reservableScheduleId) throws Exception;

    List<ReservableScheduleBaseDto> createReservation(ReservationBaseDto reservationBaseDto) throws Exception;

    List<ReservableScheduleBaseDto> deleteReservation(String reservationCode) throws Exception;

}
