package com.citi.codechallenge.controllers;

import com.citi.codechallenge.commands.CustomerForm;
import com.citi.codechallenge.domain.SuccessResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CustomerAccountControllerTest extends AbstractContrllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createCustomerWithOutSSNAndLastName() throws Exception {
        String uri = "/customer/add";
        CustomerForm customerForm = new CustomerForm();
        String inputJson = super.mapToJson(customerForm);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void createCustomerWithCustomerId() throws Exception {
        String uri = "/customer/add";
        CustomerForm customerForm = new CustomerForm();
        customerForm.setCustomerId("12345678");
        String inputJson = super.mapToJson(customerForm);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void createCustomerWithSSNAndLastName() throws Exception {
        String uri = "/customer/add";
        CustomerForm customerForm = new CustomerForm();
        customerForm.setSsn("123456789");
        customerForm.setLastName("Pitani");
        String inputJson = super.mapToJson(customerForm);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        SuccessResponse successResponse = super.mapFromJson(content, SuccessResponse.class);
        Assert.assertTrue(successResponse.getCustomerId() != null);
    }

    @Test
    public void updateCustomerWithOutSSNAndLastName() throws Exception {
        String uri = "/customer/update";
        CustomerForm customerForm = new CustomerForm();
        customerForm.setCustomerId("12345678");
        String inputJson = super.mapToJson(customerForm);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    public void createCustomerWithOutCustomerId() throws Exception {
        String uri = "/customer/update";
        CustomerForm customerForm = new CustomerForm();
        String inputJson = super.mapToJson(customerForm);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }
}
