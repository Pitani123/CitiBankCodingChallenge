package com.citi.codechallenge.controllers;

import com.citi.codechallenge.commands.AccountForm;
import com.citi.codechallenge.commands.CustomerAccountForm;
import com.citi.codechallenge.commands.CustomerForm;
import com.citi.codechallenge.domain.Account;
import com.citi.codechallenge.domain.AccountType;
import com.citi.codechallenge.domain.Customer;
import com.citi.codechallenge.domain.SuccessResponse;
import com.citi.codechallenge.services.CustomerAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.upperCase;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerAccountController {

    @Autowired
    private CustomerAccountService customerAccountService;

    @PostMapping(path = "/customer/add", consumes = "application/json")
    public SuccessResponse createCustomer(@RequestBody CustomerForm customerForm) {

        if (StringUtils.isNotBlank(customerForm.getCustomerId())) {
            throw new RuntimeException("Customer Id should be Blank or NULL.");
        }

        if (isBlank(customerForm.getSsn()) || isBlank(customerForm.getLastName())) {
            throw new RuntimeException("SSN and LastName are mandatory.");
        }

        Customer customer = convertCustomerFormToCustomer(customerForm);
        String customerId = customerAccountService.createCustomer(customer);
        SuccessResponse response = createSuccessResponse(customerId, null);
        return response;
    }

    @PostMapping(path = "/customer/update", consumes = "application/json")
    public SuccessResponse updateCustomer(@RequestBody CustomerForm customerForm) {

        if (StringUtils.isBlank(customerForm.getCustomerId())) {
            throw new RuntimeException("Customer Id should not be Blank or NULL.");
        }

        if (isBlank(customerForm.getSsn()) || isBlank(customerForm.getLastName())) {
            throw new RuntimeException("SSN and LastName are mandatory.");
        }

        Customer customer = convertCustomerFormToCustomer(customerForm);
        String customerId = customerAccountService.updateCustomer(customerForm.getCustomerId(), customer);
        SuccessResponse response = createSuccessResponse(customerId, null);
        return response;
    }

    @DeleteMapping(path = "/customer/delete", consumes = "application/json")
    public boolean deleteCustomer(@RequestParam String customerId) {

        boolean deletedCustomer = customerAccountService.deleteCustomer(customerId);
        return deletedCustomer;
    }

    @PostMapping(path = "/customer/addAccount", consumes = "application/json")
    public SuccessResponse addAccount(@RequestParam String customerId,
                                      @RequestParam AccountType accountType,
                                      @RequestParam double amount) {

        if (isBlank(customerId)) {
            throw new RuntimeException("Customer Id should be mandatory.");
        }

        if (accountType == null) {
            throw new RuntimeException("Account Type is mandatory.");
        }

        Account account = convertToAccount(accountType, amount);
        String accountId = customerAccountService.addAccount(customerId, account);
        SuccessResponse response = createSuccessResponse(customerId, accountId);
        return response;
    }

    @GetMapping(path = "/customer/detailsById")
    public CustomerAccountForm getCustomerById(@RequestParam String customerId) {
        Customer customer = customerAccountService.getCustomerById(customerId);
        System.out.println("First Name :: " + customer.getFirstName());
        System.out.println("Last Name :: " + customer.getLastName());
        System.out.println("Phone Number :: " + customer.getPhoneNumber());
        System.out.println("SSN :: " + customer.getSsn());
        CustomerAccountForm form = convertCustomerToCustomerAccountForm(customer);
        return form;
    }

    @GetMapping(path = "/customer/searchBySSNAndLastName")
    public CustomerAccountForm searchCustomerBySSNAndLastName(@RequestParam String ssn, @RequestParam String lastName) {
        Customer customer = customerAccountService.getCustomerBySSNAndLastName(upperCase(ssn), upperCase(lastName));
        if (customer != null) {
            throw new RuntimeException("Customer does not exist");
        }
        System.out.println("First Name :: " + customer.getFirstName());
        System.out.println("Last Name :: " + customer.getLastName());
        System.out.println("Phone Number :: " + customer.getPhoneNumber());
        System.out.println("SSN :: " + customer.getSsn());
        CustomerAccountForm form = convertCustomerToCustomerAccountForm(customer);
        return form;
    }

    @GetMapping(path = "/customer/searchByLastName")
    public List<CustomerAccountForm> searchCustomerByLastName(@RequestParam String lastName) {
        List<Customer> customersList = customerAccountService.getCustomerByLastName(upperCase(lastName));
        List<CustomerAccountForm> customerAccountFormsList = new ArrayList<>();

        if (customersList == null) {
            return customerAccountFormsList;
        }
        for (Customer customer : customersList) {
            System.out.println("First Name :: " + customer.getFirstName());
            System.out.println("Last Name :: " + customer.getLastName());
            System.out.println("Phone Number :: " + customer.getPhoneNumber());
            System.out.println("SSN :: " + customer.getSsn());
            CustomerAccountForm form = convertCustomerToCustomerAccountForm(customer);
            customerAccountFormsList.add(form);
        }
        return customerAccountFormsList;
    }

    @GetMapping(path = "/customer/searchByLastNameAndFirstName")
    public List<CustomerAccountForm> searchCustomerByLastNameAndFirstName(@RequestParam String lastName,
                                                                          @RequestParam String firstName) {
        List<Customer> customersList = customerAccountService.getCustomerByLastNameAndFirstName(upperCase(lastName), upperCase(firstName));
        List<CustomerAccountForm> customerAccountFormsList = new ArrayList<>();

        if (customersList == null) {
            return customerAccountFormsList;
        }
        for (Customer customer : customersList) {
            System.out.println("First Name :: " + customer.getFirstName());
            System.out.println("Last Name :: " + customer.getLastName());
            System.out.println("Phone Number :: " + customer.getPhoneNumber());
            System.out.println("SSN :: " + customer.getSsn());
            CustomerAccountForm form = convertCustomerToCustomerAccountForm(customer);
            customerAccountFormsList.add(form);
        }
        return customerAccountFormsList;
    }

    @GetMapping(path = "/customer/searchByLastNameAndPhoneNumber")
    public List<CustomerAccountForm> searchCustomerByLastNameAndPhoneNumber(@RequestParam String lastName,
                                                                            @RequestParam String phoneNumber) {
        List<Customer> customersList = customerAccountService.getCustomerByLastNameAndPhoneNumber(upperCase(lastName),
                upperCase(phoneNumber));
        List<CustomerAccountForm> customerAccountFormsList = new ArrayList<>();

        if (customersList == null) {
            return customerAccountFormsList;
        }
        for (Customer customer : customersList) {
            System.out.println("First Name :: " + customer.getFirstName());
            System.out.println("Last Name :: " + customer.getLastName());
            System.out.println("Phone Number :: " + customer.getPhoneNumber());
            System.out.println("SSN :: " + customer.getSsn());
            CustomerAccountForm form = convertCustomerToCustomerAccountForm(customer);
            customerAccountFormsList.add(form);
        }
        return customerAccountFormsList;
    }

    @PostMapping(path = "/customer/depositAmount", consumes = "application/json")
    public boolean depositAmount(@RequestParam String customerId,
                                 @RequestParam String accountId,
                                 @RequestParam double amount) {
        if (isBlank(customerId) || isBlank(accountId) || amount <= 0) {
            throw new RuntimeException("Customer Id, Account Id and amount are mandatory ");
        }

        boolean deductStatus = customerAccountService.depositAmount(customerId, accountId, amount);
        return deductStatus;
    }

    @PostMapping(path = "/customer/deductAmount", consumes = "application/json")
    public boolean deductAmount(@RequestParam String customerId,
                                @RequestParam String accountId,
                                @RequestParam double amount) {
        if (isBlank(customerId) || isBlank(accountId) || amount <= 0) {
            throw new RuntimeException("Customer Id, Account Id and amount are mandatory ");
        }

        boolean deductStatus = customerAccountService.deductAmount(customerId, accountId, amount);
        return deductStatus;
    }

    @PostMapping(path = "/customer/transferAmount", consumes = "application/json")
    public boolean transferAmount(@RequestParam String fromCustomerId,
                                  @RequestParam String fromAccountId,
                                  @RequestParam String toCustomerId,
                                  @RequestParam String toAccountId,
                                  @RequestParam double amount) {


        if (isBlank(fromCustomerId) || isBlank(fromAccountId) ||
                isBlank(toCustomerId) || isBlank(toAccountId) ||
                amount <= 0) {
            throw new RuntimeException("From CustomerId, From AccountId, To CustomerId, To AccountId and Amount are mandatory ");
        }
        boolean transfer = customerAccountService.transferFunds(fromCustomerId, fromAccountId,
                toCustomerId, toAccountId, amount);
        return transfer;
    }

    private CustomerAccountForm convertCustomerToCustomerAccountForm(Customer customer) {
        CustomerAccountForm customerAccountForm = new CustomerAccountForm();
        customerAccountForm.setFirstName(customer.getFirstName());
        customerAccountForm.setLastName(customer.getLastName());
        customerAccountForm.setPhoneNumber(customer.getPhoneNumber());
        customerAccountForm.setCustomerId(customer.get_id().toString());
        customerAccountForm.getAddress().setCity(customer.getAddress().getCity());
        customerAccountForm.getAddress().setState(customer.getAddress().getState());
        customerAccountForm.getAddress().setStreetNumberAndName(customer.getAddress().getStreetNumberAndName());
        customerAccountForm.getAddress().setZipCode(customer.getAddress().getZipCode());

        if (customer.getAccounts() == null) {
            return customerAccountForm;
        }

        for (Account account : customer.getAccounts()) {
            AccountForm accountForm = new AccountForm();
            accountForm.setAccountId(account.get_id().toString());
            accountForm.setAccountType(account.getAccountType());
            customerAccountForm.getAccounts().add(accountForm);
        }
        return customerAccountForm;
    }

    private Customer convertCustomerFormToCustomer(@RequestBody CustomerForm customerForm) {
        Customer customer = new Customer();
        customer.setFirstName(upperCase(customerForm.getFirstName()));
        customer.setLastName(upperCase(customerForm.getLastName()));
        customer.setPhoneNumber(upperCase(customerForm.getPhoneNumber()));
        customer.setSsn(upperCase(customerForm.getSsn()));
        customer.getAddress().setCity(upperCase(customerForm.getAddress().getCity()));
        customer.getAddress().setState(upperCase(customerForm.getAddress().getState()));
        customer.getAddress().setStreetNumberAndName(upperCase(customerForm.getAddress().getStreetNumberAndName()));
        customer.getAddress().setZipCode(upperCase(customerForm.getAddress().getZipCode()));
        return customer;
    }

    private Account convertToAccount(AccountType accountType, double amount) {
        Account account = new Account();
        account.setAccountType(accountType);
        account.setAmount(amount);
        return account;
    }

    private SuccessResponse createSuccessResponse(String customerId, String accountId) {
        SuccessResponse response = new SuccessResponse();
        response.setCustomerId(customerId);
        response.setAccountId(accountId);
        response.setStatus("SUCCESS");
        response.setDateTime(LocalDateTime.now());
        return response;
    }
}
