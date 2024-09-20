package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
