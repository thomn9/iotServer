package cz.reservation.app.service;

import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.DurationOfReservableTimeWindowsBaseDto;
import cz.reservation.app.model.dto.DurationOfReservableTimeWindowsDto;
import cz.reservation.app.model.entity.BusinessHours;
import cz.reservation.app.repository.JpaBusinessHoursRepository;
import cz.reservation.app.repository.JpaDurationOfReservableTimeWindowsRepository;
import cz.reservation.app.service.mapper.DurationOfReservableTimeWindowsMapper;
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
    private JpaDurationOfReservableTimeWindowsRepository durationOfReservableTimeWindowsRepository;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private DurationOfReservableTimeWindowsMapper durationOfReservableTimeWindowsMapper;

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
    public List<DurationOfReservableTimeWindowsDto> getDurationsOfReservableTimeWindows() {
        return durationOfReservableTimeWindowsRepository.findAll()
                .stream()
                .map(durationOfReservableTimeWindows -> durationOfReservableTimeWindowsMapper.fromDurationOfReservableTimeWindowsToDurationOfReservableTimeWindowsDto(durationOfReservableTimeWindows))
                .collect(Collectors.toList());

    }

    @Override
    public List<DurationOfReservableTimeWindowsDto> createDurationOfReservableTimeWindows(DurationOfReservableTimeWindowsBaseDto durationOfReservableTimeWindowsBaseDto) {
        durationOfReservableTimeWindowsRepository.save(durationOfReservableTimeWindowsMapper.toDurationOfReservableTimeWindowsFromDurationOfReservableTimeWindowsBaseDto(durationOfReservableTimeWindowsBaseDto));
        return this.getDurationsOfReservableTimeWindows();

    }

    @Transactional
    @Override
    public List<DurationOfReservableTimeWindowsDto> deleteDurationOfReservableTimeWindows(Long id) {
        durationOfReservableTimeWindowsRepository.deleteById(id);
        return this.getDurationsOfReservableTimeWindows();
    }
}
