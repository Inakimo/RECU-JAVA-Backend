package com.example.uade.tpo.dtos.response;

import com.example.uade.tpo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductResponseDto {
    Long ProductId;
    String name;
    String description;
    String brand;
    Double price;
    Integer stock;
    String sellerName;
    List<Long> categoryIds;
}
