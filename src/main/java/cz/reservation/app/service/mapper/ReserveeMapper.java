package cz.reservation.app.service.mapper;

import cz.reservation.app.model.dto.ReserveeDto;
import cz.reservation.app.model.entity.Reservee;
import org.mapstruct.Mapper;

@Mapper
public interface ReserveeMapper {

    Reservee toReserveeFromReserveeDto(ReserveeDto reserveeDto);

    ReserveeDto fromReserveeToReserveeDto(Reservee reservee);
}
