package cz.reservation.app.service.converter;

import cz.reservation.app.model.RangeOfLocalTimeFactory;
import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.entity.BusinessHours;
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
