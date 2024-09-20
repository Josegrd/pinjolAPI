package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.CustomerRequest;
import com.enigmacamp.loan_app.dto.response.CommonResponse;
import com.enigmacamp.loan_app.dto.response.CustomerResponse;
import com.enigmacamp.loan_app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathApi.CUSTOMER)
//@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse response = customerService.getCustomerById(id);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .message("Get customer by id")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomers();
        CommonResponse<List<CustomerResponse>> commonResponse = CommonResponse.<List<CustomerResponse>>builder()
                .message("Get all customers")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .message("Create customer")
                .statusCode(HttpStatus.CREATED.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(request);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .message("Update customer")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .message("Delete customer")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

}
