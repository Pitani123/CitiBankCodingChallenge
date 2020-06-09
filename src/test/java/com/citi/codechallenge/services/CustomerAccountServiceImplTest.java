package com.citi.codechallenge.services;

import com.citi.codechallenge.domain.Customer;
import com.citi.codechallenge.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerAccountServiceImplTest {

    @Autowired
    private CustomerAccountService customerAccountServiceImpl;

    @Autowired
    private CustomerRepository customerRepository;

    /* Valid SSN and Valid LastName*/
    @Test
    public void test1_getCustomerBySSNAndLastName() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerBySSNAndLastName("9987654312", "LastName");

        //then
        assertNotNull(foundCustomer.get_id());

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Valid SSN and Invalid LastName*/
    @Test
    public void test2_getCustomerBySSNAndLastName() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerBySSNAndLastName("9987654312", "LastName1");

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Invalid SSN and Valid LastName*/
    @Test
    public void test3_getCustomerBySSNAndLastName() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerBySSNAndLastName("99876543125", "LastName1");

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Null SSN and Null LastName*/
    @Test(expected = RuntimeException.class)
    public void test4_getCustomerBySSNAndLastName() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerBySSNAndLastName(null, null);

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Empty SSN and Empty LastName*/
    @Test(expected = RuntimeException.class)
    public void test5_getCustomerBySSNAndLastName() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerBySSNAndLastName("", "");

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Valid CustomerId*/
    @Test
    public void test1_getCustomerById() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerById(createdCustomer.get_id().toString());

        //then
        assertNotNull(foundCustomer.get_id());

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Invalid CustomerId*/
    @Test
    public void test2_getCustomerById() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerById("1234567");

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Null CustomerId*/
    @Test(expected = RuntimeException.class)
    public void test3_getCustomerById() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerById(null);

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    /* Empty CustomerId*/
    @Test(expected = RuntimeException.class)
    public void test4_getCustomerById() {
        //given
        Customer createdCustomer = createCustomer("FirstName1", "LastName", "1234567890", "9987654312");

        //when
        Customer foundCustomer = customerAccountServiceImpl.getCustomerById("");

        //then
        assertNull(foundCustomer);

        // clean up
        deleteCustomer(createdCustomer);
    }

    private Customer createCustomer(String firstName, String lastName, String phoneNumber, String ssn) {
        Customer customer1 = new Customer();
        customer1.setFirstName(firstName);
        customer1.setLastName(lastName);
        customer1.setPhoneNumber(phoneNumber);
        customer1.setSsn(ssn);
        customerRepository.save(customer1);
        return customer1;
    }

    private void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public void createCustomer2() {
        Customer customer2 = new Customer();
        customer2.setFirstName("FirstName2");
        customer2.setLastName("LastName");
        customer2.setPhoneNumber("1234567899");
        customer2.setSsn("9887654312");
        customerRepository.save(customer2);
    }
}
