package cz.reservation.app.service.converter;

import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservableScheduleBaseDtoToReservableScheduleConverter implements Converter<ReservableScheduleBaseDto, ReservableSchedule> {

    @Override
    public ReservableSchedule convert(ReservableScheduleBaseDto source) {
        return ReservableSchedule.builder()
                .reservationDate(source.getReservationDate())
                .reservationStart(source.getReservableTimeWindow().getMinimum())
                .reservationEnd(source.getReservableTimeWindow().getMaximum())
                .reservableState(source.getReservableState())
                .build();
    }

}
