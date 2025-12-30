package com.auctionbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "san_pham")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private String status;

    @ManyToOne
    @JoinColumn(name = "id_loai_sp")
    private Category category;
}
