package com.cleverlance.academy.tofu.iotServer.service.mapper;

import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataDetailDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservation;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {
    
    Reservation toReservationFromReservationDataDto(ReservationDataDto reservationDataDto);

    ReservationDataDetailDto fromReservationToReservationDataDetailDto(Reservation reservation);
}
