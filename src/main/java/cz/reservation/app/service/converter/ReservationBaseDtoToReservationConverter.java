package cz.reservation.app.service.converter;

import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.entity.Reservation;
import cz.reservation.app.service.mapper.ReserveeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationBaseDtoToReservationConverter implements Converter<ReservationBaseDto, Reservation> {

    @Autowired
    ReserveeMapper reserveeMapper;

    @Override
    public Reservation convert(ReservationBaseDto source) {
        return Reservation.builder()
                .reservationDate(source.getReservationDate())
                .reservationStart(source.getReservationTimeRange().getMinimum())
                .reservationEnd(source.getReservationTimeRange().getMaximum())
                .reservee(reserveeMapper.toReserveeFromReserveeDto(source.getReservee()))
                .build();
    }

}
