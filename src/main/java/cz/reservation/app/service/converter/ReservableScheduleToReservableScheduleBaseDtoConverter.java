package cz.reservation.app.service.converter;

import cz.reservation.app.model.RangeOfLocalTimeFactory;
import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservableScheduleToReservableScheduleBaseDtoConverter implements Converter<ReservableSchedule, ReservableScheduleBaseDto> {

    @Override
    public ReservableScheduleBaseDto convert(ReservableSchedule source) {
        return ReservableScheduleBaseDto.builder()
                .reservationDate(source.getReservationDate())
                .reservableTimeWindow(RangeOfLocalTimeFactory.getNewRangeOfLocalTime(source.getReservationStart(),source.getReservationEnd()))
                .reservableState(source.getReservableState())
                .serviceDefinitionId(source.getServiceDefinition().getId())
                .build();
    }
}