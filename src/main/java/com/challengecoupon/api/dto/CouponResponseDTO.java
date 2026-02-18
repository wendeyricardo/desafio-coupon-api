package com.challengecoupon.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
import com.challengecoupon.api.domain.enums.Status;

@Getter
@Builder
@AllArgsConstructor
public class CouponResponseDTO {

    @Schema(description = "ID único do cupom", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;

    @Schema(description = "Código do cupom (alfanumérico, 6 caracteres)", example = "PROMO1")
    private String code;

    @Schema(description = "Descrição do cupom", example = "Cupom de desconto para teste")
    private String description;

    @Schema(description = "Valor do desconto aplicado pelo cupom", example = "1.0")
    private Double discountValue;

    @Schema(description = "Data de expiração do cupom (AAAA-MM-DD)", example = "2027-12-31")
    private LocalDate expirationDate;

    @Schema(description = "Indica se o cupom já está publicado", example = "true")
    private Boolean published;

    @Schema(description = "Status atual do cupom (ACTIVE ou DELETED)", example = "ACTIVE")
    private Status status;
}