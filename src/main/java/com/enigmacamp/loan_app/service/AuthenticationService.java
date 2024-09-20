package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.dto.request.AuthRequest;
import com.enigmacamp.loan_app.dto.request.CustomerRequest;
import com.enigmacamp.loan_app.dto.response.LoginResponse;
import com.enigmacamp.loan_app.dto.response.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse registerCustomer(AuthRequest authRequest);
    LoginResponse login(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    RegisterResponse registerStaff(AuthRequest request);

}
