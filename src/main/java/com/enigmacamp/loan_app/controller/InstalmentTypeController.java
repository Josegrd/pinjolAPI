package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.InstalmentTypeRequest;
import com.enigmacamp.loan_app.dto.response.CommonResponse;
import com.enigmacamp.loan_app.dto.response.InstalmentTypeResponse;
import com.enigmacamp.loan_app.service.InstalmentTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(PathApi.INSTALMENT_TYPE)
@SecurityRequirement(name = "Bearer Authentication")
public class InstalmentTypeController {
    private final InstalmentTypeService instalmentTypeService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> createInstalmentType(@RequestBody InstalmentTypeRequest request) {
        InstalmentTypeResponse response = instalmentTypeService.createInstalmentType(request);
        CommonResponse <InstalmentTypeResponse> commonResponse = CommonResponse.<InstalmentTypeResponse>builder()
                .message("Create instalment type")
                .statusCode(HttpStatus.CREATED.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> updateInstalmentType(@RequestBody InstalmentTypeRequest request) {
        InstalmentTypeResponse response = instalmentTypeService.updateInstalmentType(request);
        CommonResponse <InstalmentTypeResponse> commonResponse = CommonResponse.<InstalmentTypeResponse>builder()
                .message("Update instalment type")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllInstalmentTypes() {
        List<InstalmentTypeResponse> response = instalmentTypeService.getAllInstalmentTypes();
        CommonResponse <List<InstalmentTypeResponse>> commonResponse = CommonResponse.<List<InstalmentTypeResponse>>builder()
                .message("Get all instalment types")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInstalmentTypeById(@PathVariable String id) {
        InstalmentTypeResponse response = instalmentTypeService.getInstalmentTypeById(id);
        CommonResponse <InstalmentTypeResponse> commonResponse = CommonResponse.<InstalmentTypeResponse>builder()
                .message("Get instalment type by id")
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstalmentType(@PathVariable String id) {
        instalmentTypeService.deleteInstalmentType(id);
        CommonResponse <String> commonResponse = CommonResponse.<String>builder()
                .message("Delete instalment type")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
