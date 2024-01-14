package com.assessment.qp.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class ItemUpdateRequest {
    private String name;
    private String description;
    @Min(1)
    private Integer quantity;
    private BigDecimal costPrice;
    private LocalDate expirationDate;
    private BigDecimal sellingPrice;
}
