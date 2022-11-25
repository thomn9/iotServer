package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@Data
@SuperBuilder
@NoArgsConstructor
public class DurationOfReservableTimeWindowsBaseDto {

    private Duration duration;
}
