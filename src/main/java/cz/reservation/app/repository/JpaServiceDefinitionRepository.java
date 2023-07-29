package cz.reservation.app.repository;

import cz.reservation.app.model.entity.ServiceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaServiceDefinitionRepository extends JpaRepository<ServiceDefinition, Long> {
}
