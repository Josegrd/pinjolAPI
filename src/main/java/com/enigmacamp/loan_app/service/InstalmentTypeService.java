package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.constant.EInstalmentType;
import com.enigmacamp.loan_app.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loan_app.dto.response.InstalmentTypeResponse;
import com.enigmacamp.loan_app.entity.InstalmentType;

import java.util.List;

public interface InstalmentTypeService {
    InstalmentTypeResponse createInstalmentType(InstalmentTypeRequest request);
    InstalmentTypeResponse getInstalmentTypeById(String id);
    List<InstalmentTypeResponse> getAllInstalmentTypes();
    InstalmentTypeResponse updateInstalmentType(InstalmentTypeRequest request);
    void deleteInstalmentType(String id);
    InstalmentType getById(String id);

    InstalmentType findByIdOrThrowNotFoundException(String id);
    InstalmentType getOrSave(EInstalmentType instalmentType);
}
