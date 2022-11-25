package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@SuperBuilder
@Data
public class ReservableTimeWindowDto {

    private LocalTime reservationStartTime;

    private LocalTime reservationEndTime;
}
