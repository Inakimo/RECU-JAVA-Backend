package com.example.uade.tpo.dtos.response;

import com.example.uade.tpo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderResponseDto {
    Long Id;
    UserResponseDto user;
    LocalDate orderDate;
    Double totalAmount;
    List<OrderItemResponseDto> orderItems;
}
