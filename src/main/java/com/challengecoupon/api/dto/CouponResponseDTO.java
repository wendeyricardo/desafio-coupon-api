package com.challengecoupon.api.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;
import com.challengecoupon.api.domain.enums.Status;

@Getter
@Builder
@AllArgsConstructor
public class CouponResponseDTO {
    private final UUID id;
    private final String code;
    private final String description;
    private final double discountValue;
    private final LocalDate expirationDate;
    private final boolean published;
    private final Status status;
}