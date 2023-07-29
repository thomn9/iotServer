package cz.reservation.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReservationBaseDto {

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    @JsonDeserialize(using = RangeOfLocalTimeDeserializer.class)
    @JsonSerialize(using = RangeOfLocalTimeSerializer.class)
    private Range<LocalTime> reservationTimeRange;

    @NotNull
    private ReserveeDto reservee;
}
