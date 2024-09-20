package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import com.enigmacamp.loan_app.dto.request.ApproveTransactionRequest;
import com.enigmacamp.loan_app.dto.response.TransactionResponse;
import com.enigmacamp.loan_app.entity.Customer;
import com.enigmacamp.loan_app.entity.InstalmentType;
import com.enigmacamp.loan_app.entity.LoanTransaction;
import com.enigmacamp.loan_app.entity.LoanType;
import com.enigmacamp.loan_app.repository.LoanTransactionRepository;
import com.enigmacamp.loan_app.service.CustomerService;
import com.enigmacamp.loan_app.service.InstalmentTypeService;
import com.enigmacamp.loan_app.service.LoanTypeService;
import com.enigmacamp.loan_app.service.TransactionService;
import com.enigmacamp.loan_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final LoanTransactionRepository loanTransactionRepository;
    private final ValidationUtil validationUtil;
    private final LoanTypeService loanTypeService;
    private final CustomerService customerService;
    private final InstalmentTypeService instalmentTypeService;

    @Override
    public TransactionResponse createTransaction(TransactionResponse request) {

        LoanType loanType = loanTypeService.findByIdOrThrowNotFoundException(request.getLoanTypeId());
        InstalmentType instalmentType = instalmentTypeService.findByIdOrThrowNotFoundException(request.getInstalmentTypeId());
        Customer customer = customerService.findByIdOrThrowNotFoundException(request.getCustomerId());

        try{
            validationUtil.validate(request);
            LoanTransaction loanTransaction = LoanTransaction.builder()
                    .loanType(loanType)
                    .instalmentType(instalmentType)
                    .customer(customer)
                    .nominal(request.getNominal())
                    .build();
            loanTransactionRepository.save(loanTransaction);
            return convertToResponse(loanTransaction);



        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        return convertToResponse(findByIdOrThrowNotFoundException(id));
    }

    @Override
    public TransactionResponse approveTransaction(ApproveTransactionRequest request) {
        LoanTransaction loanTransaction = findByIdOrThrowNotFoundException(request.getLoanTransactionId());

        loanTransaction.setApprovalStatus(ApprovalStatus.APPROVED);
        return convertToResponse(loanTransaction);
    }

    @Override
    public String payInstalment(MultipartFile multipartFile) {
        return "";
    }

    private LoanTransaction findByIdOrThrowNotFoundException(String id) {
        return loanTransactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private TransactionResponse convertToResponse(LoanTransaction loanTransaction) {
        return TransactionResponse.builder()
                .id(loanTransaction.getId())
                .loanTypeId(loanTransaction.getLoanType().getId())
                .instalmentTypeId(loanTransaction.getInstalmentType().getId())
                .customerId(loanTransaction.getCustomer().getId())
                .nominal(loanTransaction.getNominal())
                .approvedAt(loanTransaction.getApprovedAt())
                .approvedBy(loanTransaction.getApprovedBy())
                .approvalStatus(loanTransaction.getApprovalStatus())
//                .transactionDetailResponse(loanTransaction.getLoanTransactionDetails().stream().map(this::convertToResponse).toList())
                .createdAt(loanTransaction.getCreatedAt())
                .updatedAt(loanTransaction.getUpdatedAt())
                .build();
    }
}
