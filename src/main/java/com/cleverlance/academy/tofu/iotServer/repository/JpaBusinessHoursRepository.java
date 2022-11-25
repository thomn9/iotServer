package com.cleverlance.academy.tofu.iotServer.repository;

import com.cleverlance.academy.tofu.iotServer.model.entity.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
}
