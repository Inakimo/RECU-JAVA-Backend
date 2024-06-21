package com.example.uade.tpo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DiscountResponseDto {
    Long discountId;
    String code;
    Double percentage;
    LocalDate startDate;
    LocalDate endDate;
    boolean active;
}
