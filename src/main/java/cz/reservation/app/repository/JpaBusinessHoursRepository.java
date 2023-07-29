package cz.reservation.app.repository;

import cz.reservation.app.model.entity.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
}
