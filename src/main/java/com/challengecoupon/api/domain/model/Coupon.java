package com.challengecoupon.api.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

import com.challengecoupon.api.exception.DomainException;
import com.challengecoupon.api.domain.enums.Status;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 6, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double discountValue;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean published;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;


    // Construtor de domínio
    public Coupon(String code, String description, double discountValue, LocalDate expirationDate, boolean published) {
        this.code = cleanAndFormatCode(code);
        this.description = description;
        setDiscountValue(discountValue);
        setExpirationDate(expirationDate);
        this.published = published;
        this.status = Status.ACTIVE;
    }

    // Remove caracteres especiais e limita a 6 (preenchendo com X se necessário)
    private String cleanAndFormatCode(String code) {
        if (code == null) throw new DomainException("Código do cupom é obrigatório");
        String cleaned = code.replaceAll("[^a-zA-Z0-9]", "");
        if (cleaned.length() < 6) {
            cleaned = String.format("%-6s", cleaned).replace(' ', 'X');
        }
        return cleaned.substring(0, 6);
    }

    public void setDiscountValue(double discountValue) {
        if (discountValue < 0.5) throw new DomainException("Desconto não pode ser menor que 0.5");
        this.discountValue = discountValue;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate.isBefore(LocalDate.now()))
            throw new DomainException("Data de expiração não pode ser no passado");
        this.expirationDate = expirationDate;
    }

    public void softDelete() {
        if (this.status == Status.DELETED) throw new DomainException("Cupom já foi deletado");
        this.status = Status.DELETED;
    }
}