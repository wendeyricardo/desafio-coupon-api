package com.challengecoupon.api.service;

import com.challengecoupon.api.domain.enums.Status;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.repository.CouponRepository;
import com.challengecoupon.api.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCouponByIdService {

    private final CouponRepository repository;

    public GetCouponByIdService(CouponRepository repository) {
        this.repository = repository;
    }

    public Coupon execute(UUID id) {
        // Busca apenas cupons com status ACTIVE
        return repository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BusinessException("Cupom não encontrado"));
    }
}