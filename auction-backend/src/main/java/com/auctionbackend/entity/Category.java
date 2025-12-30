package com.auctionbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loai_san_pham")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
