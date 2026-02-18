package com.challengecoupon.api.domain.model;

import com.challengecoupon.api.domain.enums.Status;
import com.challengecoupon.api.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    void naoPermiteDescontoMenorQueMeio() {
        assertThrows(DomainException.class, () -> new Coupon(
                "PROMO1", "desc", 0.4, LocalDate.now().plusDays(1), true
        ));
    }

    @Test
    void naoPermiteDataExpirada() {
        assertThrows(DomainException.class, () -> new Coupon(
                "PROMO2", "desc", 1.0, LocalDate.now().minusDays(1), true
        ));
    }

    @Test
    void removeCaracteresEspeciaisEDefineTamanho() {
        Coupon c = new Coupon("A#B$1", "desc", 1.0, LocalDate.now().plusDays(1), false);
        assertEquals("AB1XXX", c.getCode()); // "A#B$1" -> "AB1" + "XXX" = "AB1XXX"
    }

    @Test
    void cortaCodigoMaiorQue6() {
        Coupon c = new Coupon("ABC123456", "desc", 1.0, LocalDate.now().plusDays(1), false);
        assertEquals("ABC123", c.getCode());
    }

    @Test
    void softDeleteFuncionaEUmaSoVez() {
        Coupon c = new Coupon("PROMO11", "desc", 1.0, LocalDate.now().plusDays(1), false);
        c.softDelete();
        assertEquals(Status.DELETED, c.getStatus());
        assertThrows(DomainException.class, c::softDelete);
    }

    @Test
    void criaCupomValido() {
        Coupon c = new Coupon("PROMO12", "desc", 1.0, LocalDate.now().plusDays(1), true);
        assertEquals("PROMO1", c.getCode().substring(0,6));
        assertEquals(Status.ACTIVE, c.getStatus());
    }
}