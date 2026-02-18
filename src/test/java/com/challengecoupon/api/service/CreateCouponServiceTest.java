package com.challengecoupon.api.service;

import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.exception.BusinessException;
import com.challengecoupon.api.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCouponServiceTest {
    private CouponRepository repository;
    private CreateCouponService service;

    @BeforeEach
    void setUp() {
        repository = mock(CouponRepository.class);
        service = new CreateCouponService(repository);
    }

    @Test
    void deveSalvarCupomValido() {
        Coupon cupom = new Coupon("PROMO12", "desc", 1.0, LocalDate.now().plusDays(1), true);

        when(repository.existsByCode(anyString())).thenReturn(false);
        when(repository.save(any(Coupon.class))).thenReturn(cupom);

        Coupon salvo = service.execute(cupom);

        assertEquals("PROMO1", salvo.getCode());
        verify(repository).save(cupom);
    }

    @Test
    void naoPermiteCodigoDuplicado() {
        Coupon cupom = new Coupon("PROMO12", "desc", 1.0, LocalDate.now().plusDays(1), true);

        when(repository.existsByCode(anyString())).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.execute(cupom));
        assertEquals("Já existe um cupom com esse código", ex.getMessage());
        verify(repository, never()).save(any());
    }
}