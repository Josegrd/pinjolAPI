package com.enigmacamp.loan_app.dto.response;

import com.enigmacamp.loan_app.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String email;
    private String role;
    private String token;
}
