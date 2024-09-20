package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.constant.ERole;
import com.enigmacamp.loan_app.dto.request.AuthRequest;
import com.enigmacamp.loan_app.dto.request.CustomerRequest;
import com.enigmacamp.loan_app.dto.response.LoginResponse;
import com.enigmacamp.loan_app.dto.response.RegisterResponse;
import com.enigmacamp.loan_app.entity.*;
import com.enigmacamp.loan_app.repository.UserRepository;
import com.enigmacamp.loan_app.security.JwtUtil;
import com.enigmacamp.loan_app.service.*;
import com.enigmacamp.loan_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userCredentialsRepository;
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;
    private final StaffService staffService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RegisterResponse registerCustomer(AuthRequest registerRequest) {
        try{
            validationUtil.validate(registerRequest);
            getUserByUsername(registerRequest.getEmail());
            Role role = roleService.getOrSave(Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build());

            User userCredentials = User.builder()
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(role)
                    .build();

            userCredentialsRepository.saveAndFlush(userCredentials);

            Customer customer = Customer.builder()
                    .userId(userCredentials)
                    .build();

            CustomerRequest customerRequest = CustomerRequest.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .phone(customer.getPhone())
                    .build();

            customerService.createCustomer(customerRequest);

            return RegisterResponse.builder()
                    .email(userCredentials.getEmail())
                    .role(userCredentials.getRole().getName().toString())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            validationUtil.validate(request);
            getUserByUsername(request.getEmail());
            Role role = roleService.getOrSave(Role.builder()
                .name(ERole.ROLE_ADMIN)
                .build());

            User userCredentials = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();


        userCredentialsRepository.saveAndFlush(userCredentials);

        Admin admin = Admin.builder()
                .userId(userCredentials)
                .build();

        adminService.createAdmin(admin);

        return RegisterResponse.builder()
                .email(userCredentials.getEmail())
                .role(userCredentials.getRole().getName().toString())
                .build();
    }catch (DataIntegrityViolationException e){
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exists");
    }
    }

    @Override
    public RegisterResponse registerStaff(AuthRequest request) {
        try {
            getUserByUsername(request.getEmail());
            Role role = roleService.getOrSave(Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build());

            User userCredentials = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();

            userCredentialsRepository.saveAndFlush(userCredentials);

            Staff staff = Staff.builder()
                    .userId(userCredentials)
                    .build();

            staffService.createStaff(staff);

            return RegisterResponse.builder()
                    .email(userCredentials.getEmail())
                    .role(userCredentials.getRole().getName().toString())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Staff already exists");
        }
    }


    @Override
    public LoginResponse login(AuthRequest request) {
        try{
            validationUtil.validate(request);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            AppUser appUser = (AppUser) authentication.getPrincipal();


            String token = jwtUtil.generateToken(appUser);

            return LoginResponse.builder()
                    .token(token)
                    .role(appUser.getRole().name())
                    .build();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        return null;
    }
}
