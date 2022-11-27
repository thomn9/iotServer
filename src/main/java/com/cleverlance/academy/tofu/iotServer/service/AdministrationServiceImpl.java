package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.dto.BusinessHoursDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.DurationOfReservableTimeWindowsBaseDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.DurationOfReservableTimeWindowsDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.BusinessHours;
import com.cleverlance.academy.tofu.iotServer.repository.JpaBusinessHoursRepository;
import com.cleverlance.academy.tofu.iotServer.repository.JpaDurationOfReservableTimeWindowsRepository;
import com.cleverlance.academy.tofu.iotServer.service.mapper.BusinessHoursMapper;
import com.cleverlance.academy.tofu.iotServer.service.mapper.DurationOfReservableTimeWindowsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministrationServiceImpl implements AdministrationService {

    @Autowired
    private JpaBusinessHoursRepository businessHoursRepository;
    @Autowired
    private JpaDurationOfReservableTimeWindowsRepository durationOfReservableTimeWindowsRepository;

    @Autowired
    private BusinessHoursMapper businessHoursMapper;
    @Autowired
    private DurationOfReservableTimeWindowsMapper durationOfReservableTimeWindowsMapper;

    @Override
    public List<BusinessHoursDto> getBusinessHours() {
        return businessHoursRepository.findAll()
                .stream()
                .map(businessHours -> businessHoursMapper.fromBusinessHoursToBusinessHoursDto(businessHours))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<BusinessHoursDto> setBusinessHours(List<BusinessHoursDto> businessHoursDtos) {
        //todo validate start is not before end + start != end
        businessHoursRepository.deleteAllInBatch();
        List<BusinessHours> newBusinessHours= businessHoursRepository.saveAll(businessHoursDtos
                .stream()
                .map(businessHoursDto -> businessHoursMapper.toBusinessHoursFromBusinessHoursDto(businessHoursDto))
                .collect(Collectors.toList()));
        return newBusinessHours
                .stream()
                .map(businessHours -> businessHoursMapper.fromBusinessHoursToBusinessHoursDto(businessHours))
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
