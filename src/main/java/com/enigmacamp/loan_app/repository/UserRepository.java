package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
