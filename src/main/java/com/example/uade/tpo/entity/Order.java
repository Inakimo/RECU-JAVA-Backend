package com.example.uade.tpo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
        @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;
        @Column(name = "order_date", nullable = false)
        private LocalDate orderDate;
        @Column(name = "total_amount", nullable = false)
        private Double totalAmount;
        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        @JsonManagedReference
        private List<OrderItem> orderItems;
}
