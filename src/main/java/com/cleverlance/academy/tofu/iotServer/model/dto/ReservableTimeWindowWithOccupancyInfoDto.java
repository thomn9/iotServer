package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ReservableTimeWindowWithOccupancyInfoDto extends ReservableTimeWindowDto {
    private boolean occupied;
}
