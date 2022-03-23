package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import com.cleverlance.academy.tofu.iotServer.service.MeteorologicalDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class WebController {

    private final MeteorologicalDataService meteorologicalDataService;

    public WebController(MeteorologicalDataService meteorologicalDataService) {
        this.meteorologicalDataService = meteorologicalDataService;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("home");
        model.addObject("meteorologicaldata", new MeteorologicalData());
        model.addObject("meteorologicaldatarecords", this.meteorologicalDataService.getMeteorologicalData(10,0));
        return model;
    }

    @PostMapping("/submit-form")
    public ModelAndView submitForm(@ModelAttribute MeteorologicalData meteorologicalData){
        ModelAndView model = new ModelAndView("redirect:/home");
        this.meteorologicalDataService.saveMeteorologicalData(meteorologicalData);
        return model;
    }

}
