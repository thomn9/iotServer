package cz.reservation.app.service.mapper;

import cz.reservation.app.model.dto.ServiceDefinitionBaseDto;
import cz.reservation.app.model.dto.ServiceDefinitionDto;
import cz.reservation.app.model.entity.ServiceDefinition;
import org.mapstruct.Mapper;

@Mapper
public interface ServiceDefinitionMapper {

    ServiceDefinition toServiceDefinitionFromServiceDefinitionBaseDto(ServiceDefinitionBaseDto serviceDefinitionBaseDto);

    ServiceDefinitionDto fromServiceDefinitionToServiceDefinitionDto(ServiceDefinition serviceDefinition);
}
