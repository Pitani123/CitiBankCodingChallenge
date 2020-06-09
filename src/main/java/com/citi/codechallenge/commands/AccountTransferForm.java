package com.citi.codechallenge.commands;

import com.citi.codechallenge.domain.AccountType;
import lombok.Data;

@Data
public class AccountTransferForm {
    String fromCustomerId;
    AccountType fromAccountType;
    String toCustomerId;
    AccountType toAccountType;
    double amountToTransfer;
}