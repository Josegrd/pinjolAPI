package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.AuthRequest;
import com.enigmacamp.loan_app.dto.response.CommonResponse;
import com.enigmacamp.loan_app.dto.response.LoginResponse;
import com.enigmacamp.loan_app.dto.response.RegisterResponse;
import com.enigmacamp.loan_app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathApi.AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequest request) {
        RegisterResponse response = authenticationService.registerCustomer(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .message("Register success")
                .statusCode(HttpStatus.CREATED.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        RegisterResponse response = authenticationService.registerAdmin(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .message("Register success")
                .statusCode(HttpStatus.CREATED.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/signup/staff")
    public ResponseEntity<?> registerStaff(@RequestBody AuthRequest request) {
        RegisterResponse response = authenticationService.registerStaff(request);
        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .message("Register success")
                .statusCode(HttpStatus.CREATED.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        LoginResponse response = authenticationService.login(request);
        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .message("Login success")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
