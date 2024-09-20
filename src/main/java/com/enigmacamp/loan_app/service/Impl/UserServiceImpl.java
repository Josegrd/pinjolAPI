package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.entity.AppUser;
import com.enigmacamp.loan_app.entity.User;
import com.enigmacamp.loan_app.repository.UserRepository;
import com.enigmacamp.loan_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser loadUserByUserId(String userId) {
        User userCredentials = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid cridential")); // jangan eksplisit pesannya, ntar ketahuan hacker

        AppUser appUser = AppUser.builder()
                .id(userCredentials.getId())
                .email(userCredentials.getEmail())
                .password(userCredentials.getPassword())
                .role(userCredentials.getRole().getName())
                .build();
        return appUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userCredentials = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid cridential"));

        AppUser appUser = AppUser.builder()
                .id(userCredentials.getId())
                .email(userCredentials.getEmail())
                .password(userCredentials.getPassword())
                .role(userCredentials.getRole().getName())
                .build();
        return appUser;
    }
}
