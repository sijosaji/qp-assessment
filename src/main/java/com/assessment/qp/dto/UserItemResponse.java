package com.assessment.qp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserItemResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private LocalDate expirationDate;
}
