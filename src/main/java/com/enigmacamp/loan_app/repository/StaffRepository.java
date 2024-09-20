package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, String> {
}
