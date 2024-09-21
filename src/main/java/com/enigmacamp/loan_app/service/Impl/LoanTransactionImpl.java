package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import com.enigmacamp.loan_app.constant.EInstalmentType;
import com.enigmacamp.loan_app.constant.ERole;
import com.enigmacamp.loan_app.constant.LoanStatus;
import com.enigmacamp.loan_app.dto.request.LoanTransactionRequest;
import com.enigmacamp.loan_app.entity.*;
import com.enigmacamp.loan_app.repository.LoanTransactionRepository;
import com.enigmacamp.loan_app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanTransactionImpl implements LoanTransactionService {
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanService loanService;
    private final CustomerService service;
    private final InstalmentTypeService instalmentService;
    private final RoleService roleService;

    public LoanTransaction addLoanTransaction(LoanTransactionRequest request) {
        Customer customer = service.getById(request.getCustomerId());
        LoanType loanType = loanService.getLoanTypeById(request.getLoanTypeId());
        InstalmentType instalmentType = instalmentService.getOrSave(EInstalmentType.TWELVE_MONTH);

        LoanTransaction transaction = LoanTransaction.builder()
                .customer(customer)
                .loanType(loanType)
                .instalmentType(instalmentType)
                .nominal(request.getNominal())
                .approvedAt(System.currentTimeMillis())
                .approvedBy(roleService.getOrSave(Role.builder().name(ERole.ROLE_ADMIN).build()).getName().toString())
                .approvalStatus(ApprovalStatus.APPROVED)
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();

        List<LoanTransactionDetail> transactionDetails = request.getTransactionDetails().stream()
                .map(td -> LoanTransactionDetail.builder()
                        .transactionDate(System.currentTimeMillis())
                        .nominal(td.getNominal())
                        .loanStatus(LoanStatus.UNPAID)
                        .loanTransaction(transaction)
                        .build())
                .collect(Collectors.toList());

        transaction.setLoanTransactionDetails(transactionDetails);

        return loanTransactionRepository.save(transaction);
    }


    public List<LoanTransaction> getAllLoanTransactions() {
        return loanTransactionRepository.findAll();
    }

    public LoanTransaction getLoanById(String id) {
        return findByIdOrThrowNotFound(id);

    }

    public void deleteById(String id){
        loanTransactionRepository.deleteById(id);
    }


    private LoanTransaction findByIdOrThrowNotFound(String id){
        return loanTransactionRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
    }
}
