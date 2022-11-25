package com.cleverlance.academy.tofu.iotServer.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReservationDataDetailDto extends ReservationDataDto {

    private Long id;

    private String reservationCode;
}
