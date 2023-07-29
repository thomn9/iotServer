package cz.reservation.app.model.dto;

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
    @JsonDeserialize(using = RangeOfLocalTimeDeserializer.class)
    @JsonSerialize(using = RangeOfLocalTimeSerializer.class)
    private Range<LocalTime> reservableTimeRange;

}
