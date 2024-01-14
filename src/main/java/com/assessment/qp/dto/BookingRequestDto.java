package com.assessment.qp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BookingRequestDto {
    @NotNull(message = "itemId needs to be provided")
    private UUID itemId;
    @NotNull(message = "quantity needs to be provided")
    @Min(1)
    private Integer quantity;
}
