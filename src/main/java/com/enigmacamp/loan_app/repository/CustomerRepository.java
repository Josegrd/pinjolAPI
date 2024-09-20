package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
