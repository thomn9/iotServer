package cz.reservation.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ServiceDefinitionDto extends ServiceDefinitionBaseDto implements IdentityAware<Long> {

    private Long id;

}
