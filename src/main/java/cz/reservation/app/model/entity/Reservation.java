package cz.reservation.app.model.entity;


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
@Table(name = "reservation")
public class Reservation {

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
    @Embedded
    private Reservee reservee;

    @Column(name="reservation_code")
    private String reservationCode;

}
