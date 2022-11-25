package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.dto.BusinessHoursDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.DurationOfReservableTimeWindowsBaseDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.DurationOfReservableTimeWindowsDto;

import java.util.List;

public interface AdministrationService {

    List<BusinessHoursDto> getBusinessHours();

    List<BusinessHoursDto> setBusinessHours(List<BusinessHoursDto> businessHoursDtos);

    List<DurationOfReservableTimeWindowsDto> getDurationsOfReservableTimeWindows();

    List<DurationOfReservableTimeWindowsDto> createDurationOfReservableTimeWindows(DurationOfReservableTimeWindowsBaseDto durationOfReservableTimeWindowsBaseDto);

    List<DurationOfReservableTimeWindowsDto> deleteDurationOfReservableTimeWindows(Long id);
}
