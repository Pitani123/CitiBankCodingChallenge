package com.citi.codechallenge.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessResponse {
    LocalDateTime dateTime;
    String status;
    String customerId;
    String accountId;
}