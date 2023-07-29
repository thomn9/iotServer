package cz.reservation.app.service;

import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.DurationOfReservableTimeWindowsBaseDto;
import cz.reservation.app.model.dto.DurationOfReservableTimeWindowsDto;

import java.util.List;

public interface AdministrationService {

    List<BusinessHoursDto> getBusinessHours();

    List<BusinessHoursDto> setBusinessHours(List<BusinessHoursDto> businessHoursDtos);

    List<DurationOfReservableTimeWindowsDto> getDurationsOfReservableTimeWindows();

    List<DurationOfReservableTimeWindowsDto> createDurationOfReservableTimeWindows(DurationOfReservableTimeWindowsBaseDto durationOfReservableTimeWindowsBaseDto);

    List<DurationOfReservableTimeWindowsDto> deleteDurationOfReservableTimeWindows(Long id);
}
