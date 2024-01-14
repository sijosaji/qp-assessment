package com.assessment.qp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Data
@Entity
@Table(name = "ITEM")
public class Item {
    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "COST_PRICE")
    private BigDecimal costPrice;
    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;
    @Column(name = "SELLING_PRICE")
    private BigDecimal sellingPrice;
}
