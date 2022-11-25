package com.cleverlance.academy.tofu.iotServer.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Data
@Table(name = "duration_of_reservable_time_windows")
public class DurationOfReservableTimeWindows {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "duration")
    private Duration duration;
}
