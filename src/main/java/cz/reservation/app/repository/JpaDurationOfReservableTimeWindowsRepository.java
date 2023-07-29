package cz.reservation.app.repository;

import cz.reservation.app.model.entity.DurationOfReservableTimeWindows;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaDurationOfReservableTimeWindowsRepository extends JpaRepository<DurationOfReservableTimeWindows, Long> {
}
