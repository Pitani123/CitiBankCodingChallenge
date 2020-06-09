package com.citi.codechallenge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
public class Customer {

    @Id
    private ObjectId _id;

    private String firstName;
    private String lastName;

    private Address address = new Address();

    private String phoneNumber;
    private String ssn;

    @DBRef
    private List<Account> accounts;
}
