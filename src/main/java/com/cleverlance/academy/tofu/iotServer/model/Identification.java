package com.cleverlance.academy.tofu.iotServer.model;

import lombok.Value;

@Value
public class Identification {

    private String name;
    private Address address;
    private Person owner;
}
