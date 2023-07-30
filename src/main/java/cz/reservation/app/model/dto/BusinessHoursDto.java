package cz.reservation.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    @JsonDeserialize(using = RangeOfLocalTimeDeserializer.class)
    @JsonSerialize(using = RangeOfLocalTimeSerializer.class)
    private Range<LocalTime> businessHoursTimeRange;

}