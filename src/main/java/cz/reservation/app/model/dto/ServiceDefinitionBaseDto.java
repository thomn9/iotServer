package cz.reservation.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@SuperBuilder
@NoArgsConstructor
public class ServiceDefinitionBaseDto {

    @NotNull
    private Duration duration;

    @NotNull
    private String serviceName;

}
