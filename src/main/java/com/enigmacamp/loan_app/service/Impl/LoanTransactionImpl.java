package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.constant.*;
import com.enigmacamp.loan_app.dto.request.ApproveTransactionRequest;
import com.enigmacamp.loan_app.dto.request.LoanTransactionRequest;
import com.enigmacamp.loan_app.dto.response.CommonResponse;
import com.enigmacamp.loan_app.dto.response.LoanTransactionResponse;
import com.enigmacamp.loan_app.entity.*;
import com.enigmacamp.loan_app.repository.LoanTransactionRepository;
import com.enigmacamp.loan_app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final LoanTypeService loanTypeService;
    private final AdminService adminService;
    private final UserService userService;
    int installmentCount = 0;

    public LoanTransaction addLoanTransaction(LoanTransactionRequest request) {
        Customer customer = service.getById(request.getCustomerId());
        InstalmentType instalmentType = instalmentService.getById(request.getInstalmentTypeId());
        LoanType loanType1 = loanTypeService.getById(request.getLoanTypeId());
        System.out.println(instalmentType);

        switch (instalmentType.getInstalmentType()) {
            case TWELVE_MONTH:
                installmentCount = 12;
                break;
            case SIX_MONTH:
                installmentCount = 6;
                break;
            case THREE_MONTH:
                installmentCount = 3;
                break;
            case ONE_MONTH:
                installmentCount = 1;
                break;
            case NINE_MONTH:
                installmentCount = 9;
                break;
            default:
                installmentCount = 0;
                break;
        }
//        LoanType loanType = loanService.getLoanTypeById(request.getLoanTypeId());
//        InstalmentType instalmentType = instalmentService.getOrSave(instalmentType);

        LoanTransaction transaction = LoanTransaction.builder()
                .customer(customer)
                .loanType(loanType1) // ganti ke loanType kalo gabisa
                .instalmentType(instalmentType)
                .nominal(request.getNominal())
                .approvedAt(System.currentTimeMillis())
                .approvedBy(null)
                .approvalStatus(ApprovalStatus.PENDING)
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .count(installmentCount)
                .build();

//        List<LoanTransactionDetail> transactionDetails = request.getTransactionDetails().stream()
//                .map(td -> LoanTransactionDetail.builder()
//                        .transactionDate(System.currentTimeMillis())
//                        .nominal(td.getNominal())
//                        .loanStatus(LoanStatus.UNPAID)
//                        .loanTransaction(transaction)
//                        .build())
//                .collect(Collectors.toList());
//
//        transaction.setLoanTransactionDetails(transactionDetails);

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

    public LoanTransactionResponse approveTransactionLoan(ApproveTransactionRequest request, String adminId) {
//        User user = userService.findById(adminId);

        LoanTransaction transaction = findByIdOrThrowNotFound(request.getLoanTransactionId());

        if (!transaction.getApprovalStatus().equals(ApprovalStatus.PENDING)) {
            throw new RuntimeException("Transaksi ini sudah di-approve atau ditolak.");
        }

        transaction.setApprovalStatus(ApprovalStatus.APPROVED);
        transaction.setApprovedAt(System.currentTimeMillis());

        transaction.setApprovedBy(adminId);

        loanTransactionRepository.save(transaction);

        return convertToResponse(transaction);
    }

    public LoanTransactionResponse rejectTransactionLoan(ApproveTransactionRequest request, String adminId) {
        LoanTransaction transaction = findByIdOrThrowNotFound(request.getLoanTransactionId());
        transaction.setApprovalStatus(ApprovalStatus.REJECTED);
        return convertToResponse(transaction);
    };

    public String payInstallment(String id) {
        LoanTransaction transaction = findByIdOrThrowNotFound(id);

        int currentInstallmentCount = transaction.getLoanTransactionDetails().size();

        if (currentInstallmentCount >= installmentCount) {
//            throw new RuntimeException("Semua angsuran sudah dibayar.");
            return "Semua angsuran sudah dibayar. Transaksi ditutup.";
        }


        LoanTransactionDetail newDetail = LoanTransactionDetail.builder()
                .transactionDate(System.currentTimeMillis())
                .nominal(transaction.getNominal() / installmentCount) // Nominal per angsuran
                .loanStatus(LoanStatus.PAID)
                .loanTransaction(transaction)
                .build();

        transaction.getLoanTransactionDetails().add(newDetail);


        if (transaction.getLoanTransactionDetails().size() == installmentCount) {
            transaction.setTransactionStatus(TransactionStatus.CLOSED);
        }

        loanTransactionRepository.save(transaction);

        boolean allPaid = transaction.getLoanTransactionDetails().size() == installmentCount;
        return allPaid ? "Semua angsuran sudah dibayar. Transaksi ditutup." : "Angsuran berhasil dibayar.";
        }


    private LoanTransaction findByIdOrThrowNotFound(String id){
        return loanTransactionRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
    }


    private LoanTransactionResponse convertToResponse(LoanTransaction transaction) {
        return LoanTransactionResponse.builder()
                .id(transaction.getId())
                .loanTypeId(transaction.getLoanType().getId())
                .instalmentTypeId(transaction.getInstalmentType().getId())
                .customerId(transaction.getCustomer().getId())
                .nominal(transaction.getNominal())
                .approvedAt(transaction.getApprovedAt())
                .approvalStatus(transaction.getApprovalStatus())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .approvedBy(transaction.getApprovedBy())
                .build();
    }
}
