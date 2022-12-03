package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
public class ReservableTimeWindowForADateDto {
    @NotNull
    private LocalDate date;
    @NotNull
    @Valid
    private List<ReservableTimeWindowDto> reservableTimeWindowDtos;
}
