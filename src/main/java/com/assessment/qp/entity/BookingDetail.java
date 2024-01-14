package com.assessment.qp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOOKING_DETAIL")
public class BookingDetail {
    @Id
    private UUID id;
    @Column(name = "BOOKING_ID")
    private UUID bookingId;
    private UUID itemId;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "BOOKING_ID", insertable=false, updatable=false)
    private Booking booking;
}
