package com.assessment.qp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ItemCreateRequest {
    @NotEmpty(message = "Name can't be empty")
    private String name;
    private String description;
    @Min(value = 1, message = "Min Quantity is 1")
    private Integer quantity;
    @NotNull(message = "Cost Price needs to be provided")
    private BigDecimal costPrice;
    @NotNull(message = "Expiration Date needs to be provided")
    private LocalDate expirationDate;
    @NotNull(message = "Selling Price needs to be provided")
    private BigDecimal sellingPrice;
}
