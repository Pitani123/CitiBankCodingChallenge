package com.citi.codechallenge.commands;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerAccountForm extends CustomerForm {
    private List<AccountForm> accounts = new ArrayList<>();
}