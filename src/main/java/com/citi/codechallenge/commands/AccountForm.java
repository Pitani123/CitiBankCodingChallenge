package com.citi.codechallenge.commands;

import com.citi.codechallenge.domain.AccountType;
import lombok.Data;

@Data
public class AccountForm {
    String accountId;
    AccountType accountType;
    double amount;
}
