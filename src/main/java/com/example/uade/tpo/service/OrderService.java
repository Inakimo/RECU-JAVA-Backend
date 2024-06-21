package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.entity.*;
import com.example.uade.tpo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IOrderItemRepository orderItemRepository;


    @Autowired
    private IProductRepository productRepository;


    public Optional<OrderResponseDto> getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(Mapper::convertToOrderResponseDto);
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream().map(Mapper::convertToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderDto) {
        Optional<User> userOptional = userRepository.findById(orderDto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(orderItemRequestDto -> {
            Optional<Product> productOptional = productRepository.findById(orderItemRequestDto.getProductId());
            if (productOptional.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }

            Product product = productOptional.get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequestDto.getQuantity());
            orderItem.setUnitPrice(product.getPrice() * orderItemRequestDto.getQuantity());

            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);

        double totalAmount = orderItems.stream().mapToDouble(OrderItem::getUnitPrice).sum();
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return Mapper.convertToOrderResponseDto(savedOrder);
    }

    public Boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}
