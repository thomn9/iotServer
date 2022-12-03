package com.cleverlance.academy.tofu.iotServer.service.converter;

import com.cleverlance.academy.tofu.iotServer.model.RangeOfLocalTimeFactory;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDetailDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservation;
import com.cleverlance.academy.tofu.iotServer.service.mapper.ReserveeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class ReservationToReservationDetailDtoConverter implements Converter<Reservation, ReservationDetailDto> {

    @Autowired
    ReserveeMapper reserveeMapper;
    @Override
    public ReservationDetailDto convert(Reservation source) {
        return ReservationDetailDto.builder()
                .id(source.getId())
                .reservationDate(source.getReservationDate())
                .reservationTimeRange(RangeOfLocalTimeFactory.getNewRangeOfLocalTime(source.getReservationStart(),source.getReservationEnd()))
                .reservee(reserveeMapper.fromReserveeToReserveeDto(source.getReservee()))
                .reservationCode(source.getReservationCode())
                .build();
    }
}
