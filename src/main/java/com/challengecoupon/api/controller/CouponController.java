package com.challengecoupon.api.controller;

import com.challengecoupon.api.service.CreateCouponService;
import com.challengecoupon.api.service.DeleteCouponService;
import com.challengecoupon.api.service.GetCouponByIdService;
import com.challengecoupon.api.dto.CouponRequestDTO;
import com.challengecoupon.api.dto.CouponResponseDTO;
import com.challengecoupon.api.exception.ApiErrorResponse;
import com.challengecoupon.api.domain.model.Coupon;
import com.challengecoupon.api.mapper.CouponMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CreateCouponService createCouponService;
    private final DeleteCouponService deleteCouponService;
    private final GetCouponByIdService getCouponByIdService;
    private final CouponMapper mapper;

    @Operation(summary = "Cria um novo cupom", description = "Cria um cupom conforme as regras de negócio. Código é ajustado para ser alfanumérico e 6 caracteres.")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou regra de negócio", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))) })
    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@Valid @RequestBody CouponRequestDTO request) {
        Coupon domain = mapper.toDomain(request);
        Coupon saved = createCouponService.execute(domain);
        return ResponseEntity.created(URI.create("/coupon/" + saved.getId()))
                .body(mapper.toResponse(saved));
    }

    @Operation(summary = "Remove (soft delete) um cupom pelo ID")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Cupom removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cupom não encontrado ou já removido") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCouponService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca cupom pelo ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Cupom encontrado"),
            @ApiResponse(responseCode = "400", description = "Cupom não encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getById(@PathVariable UUID id) {
        Coupon coupon = getCouponByIdService.execute(id);
        return ResponseEntity.ok(mapper.toResponse(coupon));
    }
}