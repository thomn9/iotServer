package cz.reservation.app.service.converter;

import cz.reservation.app.model.RangeOfLocalTimeFactory;
import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.model.entity.Reservation;
import cz.reservation.app.service.mapper.ReserveeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationToReservationDetailDtoConverter implements Converter<Reservation, ReservationDetailDto> {

    @Autowired
    ReserveeMapper reserveeMapper;
    @Override
    public ReservationDetailDto convert(Reservation source) {
        return ReservationDetailDto.builder()
                .id(source.getId())
                .reservationDate(source.getReservationDate())
                .reservationTimeRange(RangeOfLocalTimeFactory.getNewRangeOfLocalTime(source.getReservationStart(),source.getReservationEnd()))
                .reservee(reserveeMapper.fromReserveeToReserveeDto(source.getReservee()))
                .reservationCode(source.getReservationCode())
                .build();
    }
}
