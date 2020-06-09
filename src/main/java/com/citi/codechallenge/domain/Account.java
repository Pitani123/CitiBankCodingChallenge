package com.citi.codechallenge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Account {
    @Id
    private ObjectId _id;
    private AccountType accountType;
    private double amount;
}
