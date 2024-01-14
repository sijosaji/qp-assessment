package com.assessment.qp.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;
@Data
@Builder
public class BookingResponseDto {
    private UUID bookingId;
    private UUID userId;
    private List<UserItemResponse> bookedItems;
    private BigDecimal total;
}
