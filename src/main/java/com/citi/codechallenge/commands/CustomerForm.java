package com.citi.codechallenge.commands;

import com.citi.codechallenge.domain.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerForm {
    private String customerId;
    private String firstName;
    private String lastName;
    private String ssn;
    private String phoneNumber;
    private Address address = new Address();
}
