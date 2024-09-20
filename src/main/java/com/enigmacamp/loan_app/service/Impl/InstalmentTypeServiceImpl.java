package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.constant.EInstalmentType;
import com.enigmacamp.loan_app.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loan_app.dto.response.InstalmentTypeResponse;
import com.enigmacamp.loan_app.entity.InstalmentType;
import com.enigmacamp.loan_app.repository.InstalmentTypeRepository;
import com.enigmacamp.loan_app.service.InstalmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstalmentTypeServiceImpl implements InstalmentTypeService {
    private final InstalmentTypeRepository instalmentTypeRepository;

    @Override
    public InstalmentTypeResponse createInstalmentType(InstalmentTypeRequest request) {
        try{
            InstalmentType instalmentType = InstalmentType.builder()
                    .instalmentType(request.getInstalmentType())
                    .build();
            instalmentTypeRepository.save(instalmentType);
            return convertToResponse(instalmentType);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public InstalmentTypeResponse getInstalmentTypeById(String id) {
        return convertToResponse(findByIdOrThrowNotFoundException(id));
    }

    @Override
    public List<InstalmentTypeResponse> getAllInstalmentTypes() {
        return instalmentTypeRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Override
    public InstalmentTypeResponse updateInstalmentType(InstalmentTypeRequest request) {
        try{
            InstalmentType instalmentType = findByIdOrThrowNotFoundException(request.getId());
            instalmentType.setInstalmentType(request.getInstalmentType());
            instalmentTypeRepository.saveAndFlush(instalmentType);
            return convertToResponse(instalmentType);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteInstalmentType(String id) {
        instalmentTypeRepository.deleteById(id);
    }


    @Override
    public InstalmentType getById(String id) {
        return instalmentTypeRepository.findById(id).orElse(null);
    }

    public InstalmentType findByIdOrThrowNotFoundException(String id) {
        return instalmentTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instalment type not found"));
    }

    private InstalmentTypeResponse convertToResponse(InstalmentType instalmentType) {
        return InstalmentTypeResponse.builder()
                .Id(instalmentType.getId())
                .InstalmentType(instalmentType.getInstalmentType())
                .build();
    }

    public InstalmentType getOrSave(EInstalmentType instalmentType){
        Optional<InstalmentType> optionalInstalmentType = instalmentTypeRepository.findByInstalmentType(instalmentType);
        // role available return it
        if (optionalInstalmentType.isPresent()) {
            return optionalInstalmentType.get();
        }

        // role not available create new
        InstalmentType instalmentType1 = InstalmentType.builder()
                .instalmentType(instalmentType)
                .build();

        return instalmentTypeRepository.saveAndFlush(instalmentType1);
    }
}
