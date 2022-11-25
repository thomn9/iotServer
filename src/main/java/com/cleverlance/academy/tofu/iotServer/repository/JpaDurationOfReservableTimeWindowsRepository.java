package com.cleverlance.academy.tofu.iotServer.repository;

import com.cleverlance.academy.tofu.iotServer.model.entity.DurationOfReservableTimeWindows;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaDurationOfReservableTimeWindowsRepository extends JpaRepository<DurationOfReservableTimeWindows, Long> {
}
