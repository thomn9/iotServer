package com.cleverlance.academy.tofu.iotServer.service.converter;

import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationBaseDto;
import com.cleverlance.academy.tofu.iotServer.model.entity.Reservation;
import com.cleverlance.academy.tofu.iotServer.service.mapper.ReserveeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class ReservationBaseDtoToReservationConverter implements Converter<ReservationBaseDto, Reservation> {

    @Autowired
    ReserveeMapper reserveeMapper;

    @Override
    public Reservation convert(ReservationBaseDto source) {
        return Reservation.builder()
                .reservationDate(source.getReservationDate())
                .reservationStart(source.getReservationTimeRange().getMinimum())
                .reservationEnd(source.getReservationTimeRange().getMaximum())
                .reservee(reserveeMapper.toReserveeFromReserveeDto(source.getReservee()))
                .build();
    }

}
