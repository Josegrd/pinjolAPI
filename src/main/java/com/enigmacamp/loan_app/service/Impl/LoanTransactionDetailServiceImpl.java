package com.enigmacamp.loan_app.service.Impl;

import com.enigmacamp.loan_app.repository.LoanTransactionDetailRepository;
import com.enigmacamp.loan_app.service.LoanTransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanTransactionDetailServiceImpl implements LoanTransactionDetailService {
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;
}
