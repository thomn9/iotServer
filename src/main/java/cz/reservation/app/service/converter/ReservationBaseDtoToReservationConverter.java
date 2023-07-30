package cz.reservation.app.service.converter;

import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.entity.Reservation;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationBaseDtoToReservationConverter implements Converter<ReservationBaseDto, Reservation> {

    @Override
    public Reservation convert(ReservationBaseDto source) {
        return Reservation.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }

}
