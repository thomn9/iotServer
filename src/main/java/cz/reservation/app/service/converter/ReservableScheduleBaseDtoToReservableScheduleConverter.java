package cz.reservation.app.service.converter;

import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import cz.reservation.app.model.entity.ReservableSchedule;
import cz.reservation.app.repository.JpaServiceDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservableScheduleBaseDtoToReservableScheduleConverter implements Converter<ReservableScheduleBaseDto, ReservableSchedule> {

    @Autowired
    private JpaServiceDefinitionRepository serviceDefinitionRepository;

    @Override
    public ReservableSchedule convert(ReservableScheduleBaseDto source) {
        return ReservableSchedule.builder()
                .reservationDate(source.getReservationDate())
                .reservationStart(source.getReservableTimeWindow().getMinimum())
                .reservationEnd(source.getReservableTimeWindow().getMaximum())
                .reservableState(source.getReservableState())
                .serviceDefinition(serviceDefinitionRepository.getReferenceById(source.getServiceDefinitionId()))
                .build();
    }

}
