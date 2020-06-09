package com.citi.codechallenge.repositories;

import com.citi.codechallenge.domain.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer customer1, customer2;

    @Before
    public void saveCustomers() {
        customer1 = new Customer();
        customer1.setFirstName("FirstName1");
        customer1.setLastName("LastName");
        customer1.setPhoneNumber("1234567890");
        customer1.setSsn("9987654312");
        assertNull(customer1.get_id());
        assertEquals(customer1, customerRepository.save(customer1));
        assertNotNull(customer1.get_id());

        customer2 = new Customer();
        customer2.setFirstName("FirstName2");
        customer2.setLastName("LastName");
        customer2.setPhoneNumber("1234567899");
        customer2.setSsn("9887654312");
        assertNull(customer2.get_id());
        assertEquals(customer2, customerRepository.save(customer2));
        assertNotNull(customer2.get_id());
    }

    @After
    public void deleteCustomers() {
        customerRepository.delete(customer1);
        customerRepository.delete(customer2);
    }

    @Test
    public void test2_searchCustomerBySSNLastName() {
        assertEquals(customer1.get_id().toString(), customerRepository.findBySSNAndLastName("9987654312", "LastName").get_id().toString());
        assertEquals(customer2.get_id().toString(), customerRepository.findBySSNAndLastName("9887654312", "LastName").get_id().toString());
    }

    @Test
    public void test3_searchCustomerByLastName() {
        assertEquals(2, customerRepository.findByLastName("LastName").size());
    }

    @Test
    public void test4_searchCustomerByLastNameAndFirstName() {
        assertEquals(1, customerRepository.findByLastNameAndFirstName("LastName",
                "FirstName2").size());
        assertEquals(customer2.get_id().toString(), customerRepository.findByLastNameAndFirstName("LastName",
                "FirstName2").get(0).get_id().toString());
    }

    @Test
    public void test5_searchCustomerByLastNameAndPhoneNumber() {
        assertEquals(1, customerRepository.findByLastNameAndFirstName("LastName",
                "FirstName1").size());
        assertEquals(customer1.get_id().toString(), customerRepository.findByLastNameAndFirstName("LastName",
                "FirstName1").get(0).get_id().toString());
    }
}
