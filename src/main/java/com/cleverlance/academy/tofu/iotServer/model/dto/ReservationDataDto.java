package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReservationDataDto {

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private LocalTime reservationStartTime;

    @NotNull
    private LocalTime reservationEndTime;

    @NotNull
    private ReserveeDto reservee;
}
