package com.assessment.qp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ItemCreateRequest {
    @NotEmpty
    private String name;
    private String description;
    @Min(1)
    private Integer quantity;
    @NotNull
    private BigDecimal costPrice;
    @NotNull
    private LocalDate expirationDate;
    @NotNull
    private BigDecimal sellingPrice;
}
