package cz.reservation.app.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


@Data
@SuperBuilder
public class ReservableScheduleDto extends ReservableScheduleBaseDto implements IdentityAware<Long> {

    @NotNull
    private Long id;

}
