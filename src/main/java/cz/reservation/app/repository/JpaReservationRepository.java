package cz.reservation.app.repository;

import cz.reservation.app.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByReservationCode(String reservationCode);

    Optional<Reservation> findByReservationCode(String reservationCode);

}