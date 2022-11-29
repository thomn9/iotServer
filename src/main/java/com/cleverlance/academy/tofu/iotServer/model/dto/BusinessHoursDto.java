package com.cleverlance.academy.tofu.iotServer.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.Range;

import java.time.DayOfWeek;
import java.time.LocalTime;

@SuperBuilder
@Data
@NoArgsConstructor
public class BusinessHoursDto {

    @NonNull
    private DayOfWeek dayOfWeek;

    @NonNull
    @JsonDeserialize(using = BusinessHoursDtoDeserializer.class)
    @JsonSerialize(using = BusinessHoursDtoSerializer.class)
    private Range<LocalTime> businessHoursTimeRange;

}
