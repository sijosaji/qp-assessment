package com.assessment.qp.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookingRequestDto {
    private UUID itemId;
    private Integer quantity;
}
