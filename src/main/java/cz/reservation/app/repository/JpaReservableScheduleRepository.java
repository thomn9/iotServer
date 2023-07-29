package cz.reservation.app.repository;

import cz.reservation.app.model.entity.ReservableSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservableScheduleRepository extends JpaRepository<ReservableSchedule, Long> {
}
