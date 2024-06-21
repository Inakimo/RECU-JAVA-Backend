package com.example.uade.tpo.Utils;

import com.example.uade.tpo.dtos.response.*;
import com.example.uade.tpo.entity.*;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class Mapper {

    public static ProductResponseDto convertToProductResponseDto(Product product) {
        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setProductId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setSellerName(product.getSeller().getUsername());
        return productDto;
    }

    public static UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public static CategoryResponseDto convertToCategoryResponseDto(Category category) {
        CategoryResponseDto categoryDto = new CategoryResponseDto();
        categoryDto.setCategoryId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


    public static DiscountResponseDto convertToDiscountResponseDto(Discount save) {
        DiscountResponseDto discountDto = new DiscountResponseDto();
        discountDto.setDiscountId(save.getId());
        discountDto.setCode(save.getCode());
        discountDto.setPercentage(save.getPercentage());
        discountDto.setStartDate(save.getStartDate());
        discountDto.setEndDate(save.getEndDate());
        discountDto.setActive(save.isActive());
        return discountDto;
    }


    public static OrderResponseDto convertToOrderResponseDto(Order savedOrder) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setId(savedOrder.getId());
        orderDto.setUser(convertToUserResponseDto(savedOrder.getUser()));
        orderDto.setOrderDate(savedOrder.getOrderDate());
        orderDto.setTotalAmount(savedOrder.getTotalAmount());
        orderDto.setOrderItems(
                savedOrder.getOrderItems().stream()
                .map(Mapper::convertToOrderItemResponseDto)
                .collect(Collectors.toList())
        );
        return orderDto;
    }

    public static OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        OrderItemResponseDto orderItemDto = new OrderItemResponseDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getUnitPrice());
        return orderItemDto;
    }
}
