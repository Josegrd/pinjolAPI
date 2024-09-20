package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.constant.EInstalmentType;
import com.enigmacamp.loan_app.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, String> {
    Optional<InstalmentType> findByInstalmentType(EInstalmentType instalmentType);

}
