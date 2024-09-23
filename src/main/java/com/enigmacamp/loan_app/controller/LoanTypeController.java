package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.LoanTypeRequest;
import com.enigmacamp.loan_app.dto.response.CommonResponse;
import com.enigmacamp.loan_app.dto.response.LoanTypeResponse;
import com.enigmacamp.loan_app.service.LoanService;
import com.enigmacamp.loan_app.service.LoanTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.LOAN_TYPE)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;
    private final LoanService loanService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> createLoanType(@RequestBody LoanTypeRequest request) {
        LoanTypeResponse response = loanTypeService.createLoanType(request);
        CommonResponse<LoanTypeResponse> commonResponse = CommonResponse.<LoanTypeResponse>builder()
                .message("Create loan type")
                .statusCode(HttpStatus.CREATED.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanTypeById(@PathVariable String id) {
        LoanTypeResponse response = loanTypeService.getLoanTypeById(id);
        CommonResponse<LoanTypeResponse> commonResponse = CommonResponse.<LoanTypeResponse>builder()
                .message("Get loan type by id")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllLoanTypes() {
        List<LoanTypeResponse> response = loanTypeService.getAllLoanTypes();
        CommonResponse<List<LoanTypeResponse>> commonResponse = CommonResponse.<List<LoanTypeResponse>>builder()
                .message("Get all loan types")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> updateLoanType(@RequestBody LoanTypeRequest request) {
        LoanTypeResponse response = loanTypeService.updateLoanType(request);
        CommonResponse<LoanTypeResponse> commonResponse = CommonResponse.<LoanTypeResponse>builder()
                .message("Update loan type")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoanType(@PathVariable String id) {
        loanTypeService.deleteLoanType(id);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .message("Delete loan type")
                .statusCode(HttpStatus.OK.value())
                .data(id)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }



}
