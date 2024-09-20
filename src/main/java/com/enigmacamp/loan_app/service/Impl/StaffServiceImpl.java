package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.entity.Staff;
import com.enigmacamp.loan_app.repository.StaffRepository;
import com.enigmacamp.loan_app.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    public void createStaff(Staff staff) {
        try {
            staffRepository.saveAndFlush(staff);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Staff already exists");
        }
    }
}
