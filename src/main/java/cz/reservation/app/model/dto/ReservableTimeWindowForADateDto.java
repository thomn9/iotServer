package cz.reservation.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.commons.lang3.Range;

@Data
@SuperBuilder
public class ReservableTimeWindowForADateDto {
    @NotNull
    private LocalDate date;

    @NotNull
    @JsonDeserialize(contentUsing = RangeOfLocalTimeDeserializer.class)
    @JsonSerialize(contentUsing = RangeOfLocalTimeSerializer.class)
    private List<Range<LocalTime>> reservableTimeWindows;
}
