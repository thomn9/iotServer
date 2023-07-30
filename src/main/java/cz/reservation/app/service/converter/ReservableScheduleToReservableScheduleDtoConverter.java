package cz.reservation.app.service.converter;

import cz.reservation.app.model.RangeOfLocalTimeFactory;
import cz.reservation.app.model.dto.ReservableScheduleDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservableScheduleToReservableScheduleDtoConverter implements Converter<ReservableSchedule, ReservableScheduleDto> {

    @Override
    public ReservableScheduleDto convert(ReservableSchedule source) {
        return ReservableScheduleDto.builder()
                .id(source.getId())
                .reservationDate(source.getReservationDate())
                .reservableTimeWindow(RangeOfLocalTimeFactory.getNewRangeOfLocalTime(source.getReservationStart(),source.getReservationEnd()))
                .reservableState(source.getReservableState())
                .serviceDefinitionId(source.getServiceDefinition().getId())
                .build();
    }
}