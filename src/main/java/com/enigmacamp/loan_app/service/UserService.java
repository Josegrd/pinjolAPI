package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.entity.AppUser;
import com.enigmacamp.loan_app.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String userId);

    User findById(String userId);
}
