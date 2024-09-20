package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
