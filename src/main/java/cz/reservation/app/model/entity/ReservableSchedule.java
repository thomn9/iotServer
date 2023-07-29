package cz.reservation.app.model.entity;

import cz.reservation.app.model.ReservableState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "reservable_schedule")
public class ReservableSchedule {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_start")
    private LocalTime reservationStart;

    @Column(name = "reservation_end")
    private LocalTime reservationEnd;

    @Column(name = "reservable_state")
    @Enumerated(EnumType.STRING)
    private ReservableState reservableState;

    @ManyToOne
    @JoinColumn(name="service_definition_id", nullable=false)
    private ServiceDefinition serviceDefinition;

    @OneToOne
    @JoinColumn(name="reservation_id")
    private Reservation reservation;

}
