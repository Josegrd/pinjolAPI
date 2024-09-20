package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.dto.request.CustomerRequest;
import com.enigmacamp.loan_app.dto.response.CustomerResponse;
import com.enigmacamp.loan_app.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(CustomerRequest request);
    CustomerResponse getCustomerById(String id);
    List<CustomerResponse> getAllCustomers();
    void deleteCustomer(String id);
    Customer getById(String id);

    Customer findByIdOrThrowNotFoundException(String id);
}
