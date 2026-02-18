package com.challengecoupon.api.service;

import com.challengecoupon.api.domain.enums.Status;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.exception.BusinessException;
import com.challengecoupon.api.exception.DomainException;
import com.challengecoupon.api.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

class DeleteCouponServiceTest {
    private CouponRepository repository;
    private DeleteCouponService service;

    @BeforeEach
    void setUp() {
        repository = mock(CouponRepository.class);
        service = new DeleteCouponService(repository);
    }

    @Test
    void deveSoftDeleteCupomAtivo() {
        Coupon cupom = new Coupon("PROMO12", "desc", 1.0, LocalDate.now().plusDays(1), true);
        when(repository.findById(any())).thenReturn(Optional.of(cupom));

        service.execute(cupom.getId());

        assertEquals(Status.DELETED, cupom.getStatus());
        verify(repository).save(cupom);
    }

    @Test
    void naoPermiteDeletarCupomJaDeletado() {
        Coupon cupom = new Coupon("PROMO12", "desc", 1.0, LocalDate.now().plusDays(1), true);
        cupom.softDelete();
        when(repository.findById(any())).thenReturn(Optional.of(cupom));

        DomainException ex = assertThrows(DomainException.class, () -> service.execute(cupom.getId()));
        assertEquals("Cupom já foi deletado", ex.getMessage());
    }

    @Test
    void naoPermiteDeletarCupomInexistente() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () -> service.execute(UUID.randomUUID()));
        assertEquals("Cupom não encontrado", ex.getMessage());
    }
}