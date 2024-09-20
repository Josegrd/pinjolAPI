package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.TransactionRequest;
import com.enigmacamp.loan_app.dto.response.TransactionResponse;
import com.enigmacamp.loan_app.entity.LoanTransaction;
import com.enigmacamp.loan_app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathApi.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> requestLoan(TransactionRequest request) {
//        LoanTransaction response = transactionService.createTransaction(request);
        return null;
    }

}
