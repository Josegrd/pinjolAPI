package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.dto.request.LoanTypeRequest;
import com.enigmacamp.loan_app.dto.response.LoanTypeResponse;
import com.enigmacamp.loan_app.entity.LoanType;
import com.enigmacamp.loan_app.repository.LoanTypeRepository;
import com.enigmacamp.loan_app.service.LoanTypeService;
import com.enigmacamp.loan_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;
    private final ValidationUtil validationUtil;

    @Override
    public LoanTypeResponse createLoanType(LoanTypeRequest request) {
        try{
            validationUtil.validate(request);
            LoanType loanType = LoanType.builder()
                    .type(request.getType())
                    .maxLoan(request.getMaxLoan())
                    .build();
            loanTypeRepository.save(loanType);
            return convertToResponse(loanType);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public LoanTypeResponse getLoanTypeById(String id) {
        return convertToResponse(findByIdOrThrowNotFoundException(id));
    }

    @Override
    public List<LoanTypeResponse> getAllLoanTypes() {
        return loanTypeRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Override
    public LoanTypeResponse updateLoanType(LoanTypeRequest request) {
        try{
            validationUtil.validate(request);
            LoanType loanType = findByIdOrThrowNotFoundException(request.getId());
            loanType.setType(request.getType());
            loanType.setMaxLoan(request.getMaxLoan());
            loanTypeRepository.saveAndFlush(loanType);
            return convertToResponse(loanType);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteLoanType(String id) {
        loanTypeRepository.deleteById(id);
    }

    public LoanType findByIdOrThrowNotFoundException(String id) {
        return loanTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan type not found"));
    }

    private LoanTypeResponse convertToResponse(LoanType loanType) {
        return LoanTypeResponse.builder()
                .id(loanType.getId())
                .type(loanType.getType())
                .maxLoan(loanType.getMaxLoan())
                .build();
    }
}
