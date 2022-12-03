package com.cleverlance.academy.tofu.iotServer.service.converter;

import com.cleverlance.academy.tofu.iotServer.model.RangeOfLocalTimeFactory;
import com.cleverlance.academy.tofu.iotServer.model.dto.BusinessHoursDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.BusinessHours;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BusinessHoursToBusinessHoursDtoConverter implements Converter<BusinessHours, BusinessHoursDto> {

    @Override
    public BusinessHoursDto convert(BusinessHours source) {
        return BusinessHoursDto.builder()
                .dayOfWeek(source.getDayOfWeek())
                .businessHoursTimeRange(RangeOfLocalTimeFactory.getNewRangeOfLocalTime(source.getOpeningTime(),source.getClosingTime()))
                .build();
    }
}
