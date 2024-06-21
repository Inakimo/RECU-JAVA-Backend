package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.Discount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount, Long> {
}
