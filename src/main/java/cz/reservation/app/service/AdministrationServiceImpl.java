package cz.reservation.app.service;

import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.ServiceDefinitionBaseDto;
import cz.reservation.app.model.dto.ServiceDefinitionDto;
import cz.reservation.app.model.entity.BusinessHours;
import cz.reservation.app.repository.JpaBusinessHoursRepository;
import cz.reservation.app.repository.JpaServiceDefinitionRepository;
import cz.reservation.app.service.mapper.ServiceDefinitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministrationServiceImpl implements AdministrationService {

    @Autowired
    private JpaBusinessHoursRepository businessHoursRepository;
    @Autowired
    private JpaServiceDefinitionRepository serviceDefinitionRepository;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private ServiceDefinitionMapper serviceDefinitionMapper;

    @Override
    public List<BusinessHoursDto> getBusinessHours() {
        return businessHoursRepository.findAll()
                .stream()
                .map(businessHours -> conversionService.convert(businessHours,BusinessHoursDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<BusinessHoursDto> setBusinessHours(List<BusinessHoursDto> businessHoursDtos) {
        businessHoursRepository.deleteAllInBatch();
        List<BusinessHours> newBusinessHours= businessHoursRepository.saveAll(businessHoursDtos
                .stream()
                .map(businessHoursDto -> conversionService.convert(businessHoursDto,BusinessHours.class))
                .collect(Collectors.toList()));
        return newBusinessHours
                .stream()
                .map(businessHours -> conversionService.convert(businessHours,BusinessHoursDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDefinitionDto> getServiceDefinitions() {
        return serviceDefinitionRepository.findAll()
                .stream()
                .map(serviceDefinition -> serviceDefinitionMapper.fromServiceDefinitionToServiceDefinitionDto(serviceDefinition))
                .collect(Collectors.toList());

    }

    @Override
    public List<ServiceDefinitionDto> createServiceDefinitions(ServiceDefinitionBaseDto serviceDefinitionBaseDto) {
        serviceDefinitionRepository.save(serviceDefinitionMapper.toServiceDefinitionFromServiceDefinitionBaseDto(serviceDefinitionBaseDto));
        return this.getServiceDefinitions();

    }

    @Transactional
    @Override
    public List<ServiceDefinitionDto> deleteServiceDefinitions(Long id) {
        serviceDefinitionRepository.deleteById(id);
        return this.getServiceDefinitions();
    }
}
