package com.challengecoupon.api.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class CouponRequestDTO {
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotNull(message = "O valor do desconto é obrigatório")
    @DecimalMin(value = "0.5", message = "O valor do desconto deve ser maior ou igual a 0.5")
    private Double discountValue;
    @NotNull(message = "A data de expiração é obrigatória")
    private LocalDate expirationDate;;
    private Boolean published;
}