package com.enigmacamp.loan_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String Id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String status;
}
