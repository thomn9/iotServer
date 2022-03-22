package com.cleverlance.academy.tofu.iotServer.model.client;

import lombok.Value;

@Value
public class Identification {

    private String name;
    private Address address;
    private Owner owner;
    private String url;
}
