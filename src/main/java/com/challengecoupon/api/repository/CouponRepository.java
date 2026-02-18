package com.challengecoupon.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challengecoupon.api.domain.enums.Status;
import com.challengecoupon.api.domain.model.Coupon;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    boolean existsByCode(String code);

    Optional<Coupon> findByIdAndStatus(UUID id, Status status);
}