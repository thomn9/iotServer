package cz.reservation.app.repository;

import cz.reservation.app.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByReservationCode(String reservationCode);

    Reservation getByReservationCode(String reservationCode);

}