package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.dto.request.ApproveTransactionRequest;
import com.enigmacamp.loan_app.dto.response.TransactionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionResponse request);
    TransactionResponse getTransactionById(String id);
    TransactionResponse approveTransaction(ApproveTransactionRequest request);
    String payInstalment(MultipartFile multipartFile);
}
