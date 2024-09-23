package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.entity.Admin;
import com.enigmacamp.loan_app.repository.AdminRepository;
import com.enigmacamp.loan_app.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public void createAdmin(Admin admin) {
        try {
            adminRepository.saveAndFlush(admin);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin already exists");
        }
    }


    @Override
    public Admin getById(String id) {
        return null;
    }
}
