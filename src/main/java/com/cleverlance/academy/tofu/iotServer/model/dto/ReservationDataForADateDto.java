package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
public class ReservationDataForADateDto {
    private LocalDate date;
    private List<ReservableTimeWindowDto> reservableTimeWindowDtos;
}
