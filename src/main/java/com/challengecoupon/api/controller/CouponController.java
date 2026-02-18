package com.challengecoupon.api.controller;

import com.challengecoupon.api.service.CreateCouponService;
import com.challengecoupon.api.service.DeleteCouponService;
import com.challengecoupon.api.service.GetCouponByIdService;
import com.challengecoupon.api.dto.CouponRequestDTO;
import com.challengecoupon.api.dto.CouponResponseDTO;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.mapper.CouponMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    // Injete cada caso de uso separadamente:
    private final CreateCouponService createCouponService;
    private final DeleteCouponService deleteCouponService;
    private final GetCouponByIdService getCouponByIdService;
    private final CouponMapper mapper;

    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@Valid @RequestBody CouponRequestDTO request) {
        Coupon domain = mapper.toDomain(request);
        Coupon saved = createCouponService.execute(domain);
        return ResponseEntity.created(URI.create("/coupon/" + saved.getId()))
                .body(mapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCouponService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getById(@PathVariable UUID id) {
        Coupon coupon = getCouponByIdService.execute(id);
        return ResponseEntity.ok(mapper.toResponse(coupon));
    }
}