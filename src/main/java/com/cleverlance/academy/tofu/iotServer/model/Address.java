package com.cleverlance.academy.tofu.iotServer.model;

import lombok.Value;

@Value
public class Address {
    private String street;
    private String number;
    private String city;
}
