package com.challengecoupon.api.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
public class CouponRequestDTO {

    @Schema(description = "Código do cupom. Deve ser alfanumérico e será ajustado para 6 caracteres.", example = "PROMO1")
    @NotBlank(message = "O código é obrigatório")
    private String code;

    @Schema(description = "Descrição do cupom", example = "Cupom de desconto para teste")
    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @Schema(description = "Valor do desconto. Mínimo: 0.5", example = "1.0")
    @NotNull(message = "O valor do desconto é obrigatório")
    @DecimalMin(value = "0.5", message = "O valor do desconto deve ser maior ou igual a 0.5")
    private Double discountValue;

    @Schema(description = "Data de expiração do cupom. Não pode ser no passado.", example = "2027-12-31")
    @NotNull(message = "A data de expiração é obrigatória")
    private LocalDate expirationDate;

    @Schema(description = "Cupom já publicado?", example = "true")
    @NotNull(message = "O campo published é obrigatório")
    private Boolean published;
}