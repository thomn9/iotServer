package com.cleverlance.academy.tofu.iotServer.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@SuperBuilder
@Data
public class ReservableTimeWindowDto {

    @NotNull
    @JsonDeserialize(using = RangeOfLocatTimeDeserializer.class)
    @JsonSerialize(using = RangeOfLocalTimeSerializer.class)
    private Range<LocalTime> reservableTimeRange;

}
