package com.challengecoupon.api.service;

import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.exception.BusinessException;
import com.challengecoupon.api.repository.CouponRepository;

import org.springframework.stereotype.Service;

@Service
public class CreateCouponService {
    private final CouponRepository repository;

    public CreateCouponService(CouponRepository repository) {
        this.repository = repository;
    }

    public Coupon execute(Coupon coupon) {

        if (repository.existsByCode(coupon.getCode())) {
            throw new BusinessException("Já existe um cupom com esse código");
        }
        return repository.save(coupon);
    }
}
