package cz.reservation.app.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Data
@NoArgsConstructor
@Table(name = "service_definition")
public class ServiceDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "service_name")
    private String serviceName;
}