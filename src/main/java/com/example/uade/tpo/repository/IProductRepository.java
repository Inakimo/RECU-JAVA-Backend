package com.example.uade.tpo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.entity.Product;



@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    
}