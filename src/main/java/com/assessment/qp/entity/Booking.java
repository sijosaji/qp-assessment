package com.assessment.qp.entity;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKING")
public class Booking {
    @Id
    @Column(name = "BOOKING_ID")
    private UUID bookingId;
    private UUID userId;
    private LocalDateTime bookingTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingDetail> bookingDetailList;

}
