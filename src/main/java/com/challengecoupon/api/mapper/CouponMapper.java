package com.challengecoupon.api.mapper;

import org.springframework.stereotype.Component;

import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.dto.CouponRequestDTO;
import com.challengecoupon.api.dto.CouponResponseDTO;

@Component
public class CouponMapper {
    public Coupon toDomain(CouponRequestDTO req) {
        return new Coupon(
                req.getCode(),
                req.getDescription(),
                req.getDiscountValue(),
                req.getExpirationDate(),
                Boolean.TRUE.equals(req.getPublished())
        );
    }

    public CouponResponseDTO toResponse(Coupon coupon) {
        return CouponResponseDTO.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .description(coupon.getDescription())
                .discountValue(coupon.getDiscountValue())
                .expirationDate(coupon.getExpirationDate())
                .status(coupon.getStatus())
                .published(coupon.isPublished())
                .build();
    }
}