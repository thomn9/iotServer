package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@SuperBuilder
@Data
@NoArgsConstructor
public class BusinessHoursDto {

    @NonNull
    private DayOfWeek dayOfWeek;

    @NonNull
    private LocalTime openingTime;

    @NonNull
    private LocalTime closingTime;
}
