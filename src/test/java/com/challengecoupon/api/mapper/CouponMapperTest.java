package com.challengecoupon.api.mapper;

import com.challengecoupon.api.domain.enums.Status;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.dto.CouponRequestDTO;
import com.challengecoupon.api.dto.CouponResponseDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CouponMapperTest {

    private final CouponMapper mapper = new CouponMapper();

    @Test
    void testToDomainRemoveCaracteresEspeciaisEFormataCodigo() {
        CouponRequestDTO dto = new CouponRequestDTO();
        dto.setCode("A#B1");
        dto.setDescription("Desc");
        dto.setDiscountValue(1.0);
        dto.setExpirationDate(LocalDate.now().plusDays(1));
        dto.setPublished(true);

        Coupon coupon = mapper.toDomain(dto);

        assertEquals("AB1XXX", coupon.getCode());
        assertEquals("Desc", coupon.getDescription());
        assertEquals(1.0, coupon.getDiscountValue());
        assertTrue(coupon.isPublished());
        assertEquals(LocalDate.now().plusDays(1), coupon.getExpirationDate());
    }

    @Test
    void testToResponse() {
        Coupon coupon = new Coupon("PROMO12", "Desc", 2.0, LocalDate.now().plusDays(1), true);
        
        UUID id = UUID.randomUUID();
        coupon = setIdAndStatusForTest(coupon, id, Status.ACTIVE);

        CouponResponseDTO dto = mapper.toResponse(coupon);

        assertEquals(id, dto.getId());
        assertEquals("PROMO1", dto.getCode());
        assertEquals("Desc", dto.getDescription());
        assertEquals(2.0, dto.getDiscountValue());
        assertEquals(coupon.getExpirationDate(), dto.getExpirationDate());
        assertEquals(Status.ACTIVE, dto.getStatus());
        assertTrue(dto.getPublished());
    }

    private Coupon setIdAndStatusForTest(Coupon coupon, UUID id, Status status) {
        try {
            java.lang.reflect.Field idField = Coupon.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(coupon, id);

            java.lang.reflect.Field statusField = Coupon.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(coupon, status);

            return coupon;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}