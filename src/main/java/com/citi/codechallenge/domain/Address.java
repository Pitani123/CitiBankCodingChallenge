package com.citi.codechallenge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {
    private String streetNumberAndName;
    private String city;
    private String state;
    private String zipCode;
}
