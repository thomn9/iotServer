package cz.reservation.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.reservation.app.model.ReservableState;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@SuperBuilder
public class ReservableScheduleBaseDto {

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    @JsonDeserialize(using = RangeOfLocalTimeDeserializer.class)
    @JsonSerialize(using = RangeOfLocalTimeSerializer.class)
    private Range<LocalTime> reservableTimeWindow;

    @NotNull
    private ReservableState reservableState;

    @NotNull
    private Long serviceDefinitionId;
}
