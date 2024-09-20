package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.constant.ERole;
import com.enigmacamp.loan_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(ERole name);
}
