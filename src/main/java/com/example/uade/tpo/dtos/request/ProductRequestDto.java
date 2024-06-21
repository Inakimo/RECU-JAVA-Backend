package com.example.uade.tpo.dtos.request;

import com.example.uade.tpo.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequestDto {
    String name;
    String description;
    String brand;
    Double price;
    Integer stock;
    List<Long> categoryIds;
}
