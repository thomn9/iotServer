package cz.reservation.app.repository;

import cz.reservation.app.model.entity.ReservableSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaReservableScheduleRepository extends JpaRepository<ReservableSchedule, Long> {

    ReservableSchedule findByReservation(Long reservationId);

    Optional<ReservableSchedule> findBySessionId(UUID sessionId);

    Optional<ReservableSchedule> findBySessionIdAndId(UUID sessionId, Long id);

    List<ReservableSchedule> findByReservationDateAndServiceDefinitionId(LocalDate reservationDate, Long serviceDefinitionId);
}
