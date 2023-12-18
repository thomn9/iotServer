package cz.reservation.app.service.converter;

import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.model.entity.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationToReservationDetailDtoConverter implements Converter<Reservation, ReservationDetailDto> {

    @Override
    public ReservationDetailDto convert(Reservation source) {
        return ReservationDetailDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .reservationCode(source.getReservationCode())
                //todo impl real res code
                .reservableScheduleId(1L)
                .build();
    }

}
