package com.challengecoupon.api.service;

import com.challengecoupon.api.repository.CouponRepository;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCouponService {

    private final CouponRepository repository;

    public DeleteCouponService(CouponRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Cupom não encontrado"));
        coupon.softDelete(); // Regra de domínio
        repository.save(coupon); // Soft delete (status = DELETED)
    }
}