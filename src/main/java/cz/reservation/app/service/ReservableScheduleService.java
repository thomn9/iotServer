package cz.reservation.app.service;

import cz.reservation.app.model.dto.ReservableScheduleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservableScheduleService {
    void createReservableSchedule(Long serviceDefinitionId);

    List<ReservableScheduleDto> getReservableSchedule();

}
