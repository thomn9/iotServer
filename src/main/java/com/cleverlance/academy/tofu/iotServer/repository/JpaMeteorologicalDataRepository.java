package com.cleverlance.academy.tofu.iotServer.repository;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMeteorologicalDataRepository extends JpaRepository<MeteorologicalData, Integer> {

}
