package cz.reservation.app.repository;

import cz.reservation.app.model.entity.ReservableSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface JpaReservableScheduleRepository extends JpaRepository<ReservableSchedule, Long> {

    ReservableSchedule findByReservation(Long reservationId);

    Optional<ReservableSchedule> findBySessionId(UUID sessionId);
}
