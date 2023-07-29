package cz.reservation.app.repository;

import cz.reservation.app.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByReservationCode(String reservationCode);

    Reservation getByReservationCode(String reservationCode);

    List<Reservation> findByReservationDateBetween(LocalDate from, LocalDate to);

    List<Reservation> findByReservationDate(LocalDate date);
}