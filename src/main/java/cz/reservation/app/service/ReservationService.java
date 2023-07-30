package cz.reservation.app.service;


import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.dto.ReservableScheduleDto;
import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;

import java.util.List;

public interface ReservationService {

    ReservationDetailDto getReservationDetail(String reservationCode) throws Exception;

    List<ReservableScheduleDto> lockReservableSchedule(Long reservableScheduleId) throws Exception;

    List<ReservableScheduleDto> createReservation(ReservationBaseDto reservationBaseDto) throws Exception;

    List<ReservableScheduleDto> deleteReservation(String reservationCode) throws Exception;

}
