package com.enigmacamp.loan_app.dto.request;

import com.enigmacamp.loan_app.constant.EInstalmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstalmentTypeRequest {
    private String id;
    private EInstalmentType InstalmentType;
}
