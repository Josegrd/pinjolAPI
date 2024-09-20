package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, String> {
}
