package cz.reservation.app.service.converter;

import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.entity.BusinessHours;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BusinessHoursDtoToBusinessHoursConverter implements Converter<BusinessHoursDto, BusinessHours> {

    @Override
    public BusinessHours convert(BusinessHoursDto source) {

        return BusinessHours.builder()
                .dayOfWeek(source.getDayOfWeek())
                .openingTime(source.getBusinessHoursTimeRange().getMinimum())
                .closingTime(source.getBusinessHoursTimeRange().getMaximum())
                .build();
    }
}