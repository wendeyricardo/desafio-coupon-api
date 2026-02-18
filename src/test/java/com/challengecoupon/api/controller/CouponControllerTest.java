package com.challengecoupon.api.controller;

import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.dto.CouponResponseDTO;
import com.challengecoupon.api.exception.BusinessException;
import com.challengecoupon.api.mapper.CouponMapper;
import com.challengecoupon.api.service.CreateCouponService;
import com.challengecoupon.api.service.DeleteCouponService;
import com.challengecoupon.api.service.GetCouponByIdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.UUID;

@WebMvcTest(CouponController.class)
class CouponControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CreateCouponService createCouponService;

        @MockBean
        private DeleteCouponService deleteCouponService;

        @MockBean
        private GetCouponByIdService getCouponByIdService;

        @MockBean
        private CouponMapper couponMapper;

@Test
void deveCriarCupomComSucesso() throws Exception {
    String payload = """ 
    { "code": "PROMO1", "description": "desc", "discountValue":
     1.0, "expirationDate": "2027-11-30", "published": true } """;

    Coupon fakeCoupon = new Coupon("PROMO1", "desc", 1.0, LocalDate.parse("2027-11-30"), true);

    when(couponMapper.toDomain(any())).thenReturn(fakeCoupon);
    when(createCouponService.execute(any())).thenReturn(fakeCoupon);
    when(couponMapper.toResponse(any())).thenReturn(
        CouponResponseDTO.builder()
            .code("PROMO1")
            .description("desc")
            .discountValue(1.0)
            .expirationDate(LocalDate.parse("2027-11-30"))
            .published(true)
            .build()
    );

    mockMvc.perform(post("/coupon")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("PROMO1"))
            .andExpect(jsonPath("$.discountValue").value(1.0));
}

        @Test
        void deveRetornar400SeDescontoMenorQueMeio() throws Exception {
                String payload = """
                                { "code": "PROMO1", "description": "Teste", "discountValue":
                                        0.4, "expirationDate": "2027-11-30", "published": true } """;

                mockMvc.perform(post("/coupon")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.fields.discountValue").exists());
        }

        @Test
        void deveRetornar404QuandoCupomNaoEncontrado() throws Exception {
                UUID id = UUID.randomUUID();
                when(getCouponByIdService.execute(id)).thenThrow(new BusinessException("Cupom não encontrado"));

                mockMvc.perform(get("/coupon/" + id))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("Cupom não encontrado"));
        }

        @Test
        void deveRetornar400AoDeletarCupomInexistente() throws Exception {
                UUID id = UUID.randomUUID();
                doThrow(new BusinessException("Cupom não encontrado")).when(deleteCouponService).execute(id);

                mockMvc.perform(delete("/coupon/" + id))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("Cupom não encontrado"));
        }

        @Test
        void deveRetornar400QuandoBuscarCupomDeletado() throws Exception {
                UUID id = UUID.randomUUID();
                when(getCouponByIdService.execute(id)).thenThrow(new BusinessException("Cupom não encontrado"));

                mockMvc.perform(get("/coupon/" + id))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value("Cupom não encontrado"));
        }
}