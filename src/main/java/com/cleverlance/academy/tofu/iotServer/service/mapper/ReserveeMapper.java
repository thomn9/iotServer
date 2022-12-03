package com.cleverlance.academy.tofu.iotServer.service.mapper;

import com.cleverlance.academy.tofu.iotServer.model.dto.ReserveeDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservee;
import org.mapstruct.Mapper;

@Mapper
public interface ReserveeMapper {

    Reservee toReserveeFromReserveeDto(ReserveeDto reserveeDto);

    ReserveeDto fromReserveeToReserveeDto(Reservee reservee);
}
