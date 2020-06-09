package com.citi.codechallenge.repositories;

import com.citi.codechallenge.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{ 'ssn' : ?0, 'lastName' : ?1 }")
    Customer findBySSNAndLastName(String ssn, String lastName);

    @Query("{ 'lastName' : ?0 }")
    List<Customer> findByLastName(String lastName);

    @Query("{ 'lastName' : ?0, 'firstName' : ?1 }")
    List<Customer> findByLastNameAndFirstName(String lastName, String firstName);

    @Query("{ 'lastName' : ?0, 'phoneNumber' : ?1 }")
    List<Customer> findByLastNameAndPhoneNumber(String lastName, String phoneNumber);
}