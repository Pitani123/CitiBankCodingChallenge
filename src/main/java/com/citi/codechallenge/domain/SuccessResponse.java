package com.citi.codechallenge.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessResponse {
    String status;
    String customerId;
    String accountId;
}