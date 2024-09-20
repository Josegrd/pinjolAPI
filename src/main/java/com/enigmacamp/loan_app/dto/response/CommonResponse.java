package com.enigmacamp.loan_app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse <T> {
    private Integer statusCode;
    private String message;
    private T data;
}
