package com.cleverlance.academy.tofu.iotServer;

import com.cleverlance.academy.tofu.iotServer.service.RegistrationService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ContextEventListener {

    private final RegistrationService registrationService;

    public ContextEventListener(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @EventListener
    public void handleContextRefreshedEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.registrationService.registerToAggregatorOnStartUp();
    }
}
