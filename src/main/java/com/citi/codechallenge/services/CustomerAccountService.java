package com.citi.codechallenge.services;

import com.citi.codechallenge.domain.Account;
import com.citi.codechallenge.domain.Customer;

import java.util.List;

public interface CustomerAccountService {

    public Customer getCustomerBySSNAndLastName(String ssn, String lastName);

    public List<Customer> getCustomerByLastName(String lastName);

    public List<Customer> getCustomerByLastNameAndFirstName(String lastName, String firstName);

    public List<Customer> getCustomerByLastNameAndPhoneNumber(String lastName, String phoneNumber);

    public Customer getCustomerById(String customerId);

    public String createCustomer(Customer customer);

    public String addAccount(String customerId, Account account);

    public String updateCustomer(String customerId, Customer customer);

    public boolean deleteCustomer(String customerId);

    public boolean depositAmount(String customerId, String accountId, double amount);

    public boolean deductAmount(String customerId, String accountId, double amount);

    public boolean deleteAccount(String customerId, String accountId);

    public boolean transferFunds(String fromCustomerId, String fromAccountId, String toCustomerId, String toAccountId, double amount);
}
