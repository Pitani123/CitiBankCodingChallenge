package com.citi.codechallenge.services;

import com.citi.codechallenge.domain.Account;
import com.citi.codechallenge.domain.Customer;
import com.citi.codechallenge.repositories.AccountRepository;
import com.citi.codechallenge.repositories.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Customer getCustomerBySSNAndLastName(String ssn, String lastName) {
        if (StringUtils.isBlank(ssn) || StringUtils.isBlank(lastName)) {
            throw new RuntimeException("SSN or LastName should not be blank or null");
        }

        Customer foundCustomer = customerRepository.findBySSNAndLastName(ssn, lastName);
        return foundCustomer;
    }

    @Override
    public List<Customer> getCustomerByLastName(String lastName) {
        if (StringUtils.isBlank(lastName)) {
            throw new RuntimeException("LastName should not be blank or null");
        }

        List<Customer> foundCustomers = customerRepository.findByLastName(lastName);
        return foundCustomers;
    }

    @Override
    public List<Customer> getCustomerByLastNameAndFirstName(String lastName, String firstName) {
        if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
            throw new RuntimeException("FirstName or LastName should not be blank or null");
        }

        List<Customer> foundCustomers = customerRepository.findByLastNameAndFirstName(lastName, firstName);
        return foundCustomers;
    }

    @Override
    public List<Customer> getCustomerByLastNameAndPhoneNumber(String lastName, String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(lastName)) {
            throw new RuntimeException("PhoneNumber or LastName should not be blank or null");
        }
        List<Customer> foundCustomers = customerRepository.findByLastNameAndPhoneNumber(lastName, phoneNumber);
        return foundCustomers;
    }

    @Override
    public Customer getCustomerById(String customerId) {
        if (StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id should not be blank or null");
        }

        System.out.println("Customer Id :: " + customerId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            return null;
        }
    }

    @Override
    public String createCustomer(Customer customer) {
        if (customer == null) {
            throw new RuntimeException("Customer Object should not be null");
        }

        Customer findCustomer = customerRepository.findBySSNAndLastName(customer.getSsn(), customer.getLastName());

        if (findCustomer != null) {
            throw new RuntimeException("Customer already exist");
        }

        customerRepository.save(customer);
        System.out.println("Customer Id :: " + customer.get_id().toString());
        return customer.get_id().toString();
    }

    @Override
    public String updateCustomer(String customerId, Customer customer) {

        if (customer == null || StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id and Customer Object should not be null");
        }

        // If Customer does not exist with SSN and Last name, then return error.
        Customer findCustomer = customerRepository.findBySSNAndLastName(customer.getSsn(), customer.getLastName());

        if (findCustomer == null) {
            throw new RuntimeException("Customer does not exist");
        }

        if (!customer.getSsn().equals(findCustomer.getSsn()) || !customer.getLastName().equals(findCustomer.getLastName())) {
            throw new RuntimeException("Another Customer exist with same SSN and LastName");
        }

        // If not, update Customer Phone Number and Address Only
        customerRepository.save(customer);
        System.out.println("Customer Id :: " + customer.get_id().toString());
        return customer.get_id().toString();
    }

    @Override
    public String addAccount(String customerId, Account account) {

        if (account == null || StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id and Account Object should not be null");
        }

        System.out.println("Customer Id :: " + customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id should not be blank.");
        }

        Customer findCustomer = customerRepository.findById(customerId).get();
        if (findCustomer == null) {
            throw new RuntimeException("Customer does not exist.");
        }

        if (findCustomer.getAccounts() == null) {
            findCustomer.setAccounts(new ArrayList<>());
        }

        if (findCustomer.getAccounts().size() >= 3) {
            throw new RuntimeException("Customer is already having 3 accounts.");
        }

        for (Account existingAccount : findCustomer.getAccounts()) {
            if (existingAccount.getAccountType() == account.getAccountType()) {
                throw new RuntimeException("Customer Account Type already exist.");
            }
        }

        findCustomer.getAccounts().add(account);
        accountRepository.save(account);
        customerRepository.save(findCustomer);
        return account.get_id().toString();
    }

    @Override
    @Transactional
    public boolean depositAmount(String customerId, String accountId, double amount) {
        System.out.println("Customer Id :: " + customerId);
        if (StringUtils.isBlank(customerId) || StringUtils.isBlank(accountId)) {
            throw new RuntimeException("Account Id or Customer Id should not be blank.");
        }

        Customer findCustomer = customerRepository.findById(customerId).get();
        if (findCustomer == null) {
            throw new RuntimeException("Customer does not exist.");
        }

        if (findCustomer.getAccounts() == null || findCustomer.getAccounts().size() == 0) {
            throw new RuntimeException("Customer dont have any accounts.");
        }

        for (Account existingAccount : findCustomer.getAccounts()) {
            if (existingAccount.get_id().toString().equals(accountId)) {
                existingAccount.setAmount(existingAccount.getAmount() + amount);
                accountRepository.save(existingAccount);
                return true;
            }
        }
        throw new RuntimeException("Customer dont have any matching account id.");
    }

    @Override
    @Transactional
    public boolean deductAmount(String customerId, String accountId, double amount) {
        System.out.println("Customer Id :: " + customerId);

        if (StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id should not be blank.");
        }

        Customer findCustomer = customerRepository.findById(customerId).get();
        if (findCustomer == null) {
            throw new RuntimeException("Customer does not exist.");
        }

        if (findCustomer.getAccounts() == null || findCustomer.getAccounts().size() == 0) {
            throw new RuntimeException("Customer dont have any accounts.");
        }

        for (Account existingAccount : findCustomer.getAccounts()) {
            if (existingAccount.get_id().toString().equals(accountId) &&
                    existingAccount.getAmount() >= amount) {
                existingAccount.setAmount(existingAccount.getAmount() - amount);
                accountRepository.save(existingAccount);
                return true;
            }
        }
        throw new RuntimeException("Customer dont have any matching account id.");
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        System.out.println("Customer Id :: " + customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id should not be blank.");
        }

        Customer findCustomer = customerRepository.findById(customerId).get();
        if (findCustomer == null) {
            throw new RuntimeException("Customer does not exist.");
        }

        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public boolean deleteAccount(String customerId, String accountId) {
        System.out.println("Customer Id :: " + customerId);
        if (StringUtils.isBlank(customerId)) {
            throw new RuntimeException("Customer Id should not be blank.");
        }

        Customer findCustomer = customerRepository.findById(customerId).get();
        if (findCustomer == null) {
            throw new RuntimeException("Customer does not exist.");
        }

        if (findCustomer.getAccounts() == null || findCustomer.getAccounts().size() == 0) {
            throw new RuntimeException("Customer dont have any accounts.");
        }

        for (Account existingAccount : findCustomer.getAccounts()) {
            if (existingAccount.get_id().toString().equals(accountId)) {
                accountRepository.deleteById(accountId);
                return true;
            }
        }
        throw new RuntimeException("Customer dont have any matching account id.");
    }

    @Override
    @Transactional
    public boolean transferFunds(String fromCustomerId, String fromAccountId, String toCustomerId, String toAccountId, double amount) {
        boolean deductStatus = deductAmount(fromCustomerId, fromAccountId, amount);
        if (deductStatus) {
            boolean depositStatus = depositAmount(toCustomerId, toAccountId, amount);
            return depositStatus;
        }
        return false;
    }
}
