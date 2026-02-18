package com.challengecoupon.api.exception;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private Instant timestamp = Instant.now();

    public ApiErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
    
}