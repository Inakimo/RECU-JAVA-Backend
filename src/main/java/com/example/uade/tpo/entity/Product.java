package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private String brand;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User seller;
    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "category_id"})
    )
    private List<Category> categories;


}
