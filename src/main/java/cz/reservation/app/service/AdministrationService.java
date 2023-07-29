package cz.reservation.app.service;

import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.ServiceDefinitionBaseDto;
import cz.reservation.app.model.dto.ServiceDefinitionDto;

import java.util.List;

public interface AdministrationService {

    List<BusinessHoursDto> getBusinessHours();

    List<BusinessHoursDto> setBusinessHours(List<BusinessHoursDto> businessHoursDtos);

    List<ServiceDefinitionDto> getServiceDefinitions();

    List<ServiceDefinitionDto> createServiceDefinitions(ServiceDefinitionBaseDto serviceDefinitionBaseDto);

    List<ServiceDefinitionDto> deleteServiceDefinitions(Long id);
}
