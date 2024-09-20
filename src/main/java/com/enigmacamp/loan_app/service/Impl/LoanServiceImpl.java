package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.entity.LoanType;
import com.enigmacamp.loan_app.repository.LoanTypeRepository;
import com.enigmacamp.loan_app.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanTypeRepository loanTypeRepository;

    public LoanType addLoan(LoanType loan){

        LoanType loanType = LoanType.builder()
                .id(loan.getId())
                .type(loan.getType())
                .maxLoan(loan.getMaxLoan())
                .customer(loan.getCustomer())
                .build();

        return loanTypeRepository.save(loanType);
    }

    public List<LoanType> getAllLoan(){
        return loanTypeRepository.findAll();
    }

    public LoanType updateLoan(LoanType loan){
        Optional<LoanType> findLoan = loanTypeRepository.findById(loan.getId());
        if (findLoan.isPresent()){
            LoanType loanType = findLoan.get();
            loanType.setType(loan.getType());
            loanType.setMaxLoan(loan.getMaxLoan());
            return loanTypeRepository.save(loanType);
        }else {
            throw new IllegalStateException("Couldn't find loan'");
        }
    }

    public void deleteById(String id){
        loanTypeRepository.deleteById(id);
    }

    public LoanType getLoanTypeById(String id){
        return findByIdOrThrowNotFound(id);
    }

    private LoanType findByIdOrThrowNotFound(String id){
        return loanTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found with id " + id));
    }
}