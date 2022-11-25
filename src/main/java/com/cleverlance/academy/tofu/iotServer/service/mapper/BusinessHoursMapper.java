package com.cleverlance.academy.tofu.iotServer.service.mapper;

import com.cleverlance.academy.tofu.iotServer.model.dto.BusinessHoursDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.BusinessHours;
import org.mapstruct.Mapper;

@Mapper
public interface BusinessHoursMapper {

    BusinessHours toBusinessHoursFromBusinessHoursDto(BusinessHoursDto businessHoursDto);

    BusinessHoursDto fromBusinessHoursToBusinessHoursDto(BusinessHours businessHours);
}
