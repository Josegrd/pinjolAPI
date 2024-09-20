package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.dto.request.CustomerRequest;
import com.enigmacamp.loan_app.dto.response.CustomerResponse;
import com.enigmacamp.loan_app.entity.Customer;
import com.enigmacamp.loan_app.repository.CustomerRepository;
import com.enigmacamp.loan_app.service.CustomerService;
import com.enigmacamp.loan_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        try{
            validationUtil.validate(request);
            Customer customer = Customer.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .dateOfBirth(request.getDateOfBirth())
                    .phone(request.getPhone())
                    .status(request.getStatus())
                    .build();
            customerRepository.save(customer);
            return convertToResponse(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest request) {
        try{
            validationUtil.validate(request);
            Customer customer = findByIdOrThrowNotFoundException(request.getId());
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setDateOfBirth(request.getDateOfBirth());
            customer.setPhone(request.getPhone());
            customer.setStatus(request.getStatus());
            customerRepository.saveAndFlush(customer);
            return convertToResponse(customer);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return convertToResponse(findByIdOrThrowNotFoundException(id));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customer = findByIdOrThrowNotFoundException(id);
        customer.setIsActive(false);
        customerRepository.delete(customer);
    }

    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Customer findByIdOrThrowNotFoundException(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .build();
    }
}
