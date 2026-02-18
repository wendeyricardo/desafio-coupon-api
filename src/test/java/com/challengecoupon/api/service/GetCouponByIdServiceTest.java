package com.challengecoupon.api.service;


import com.challengecoupon.api.domain.enums.Status;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.exception.BusinessException;
import com.challengecoupon.api.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

class GetCouponByIdServiceTest {
    private CouponRepository repository;
    private GetCouponByIdService service;

    @BeforeEach
    void setUp() {
        repository = mock(CouponRepository.class);
        service = new GetCouponByIdService(repository);
    }

    @Test
    void retornaCupomAtivo() {
        Coupon cupom = new Coupon("PROMO12", "desc", 1.0, LocalDate.now().plusDays(1), true);
        when(repository.findByIdAndStatus(any(), eq(Status.ACTIVE)))
            .thenReturn(Optional.of(cupom));

        Coupon result = service.execute(cupom.getId());
        assertEquals(cupom, result);
    }

    @Test
    void naoRetornaCupomDeletadoOuInexistente() {
        when(repository.findByIdAndStatus(any(), eq(Status.ACTIVE)))
            .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.execute(UUID.randomUUID()));
        assertEquals("Cupom não encontrado", ex.getMessage());
    }
}