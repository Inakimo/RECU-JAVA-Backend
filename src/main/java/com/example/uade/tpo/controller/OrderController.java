package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.OrderRequestDto;
import com.example.uade.tpo.dtos.response.OrderResponseDto;
import com.example.uade.tpo.dtos.response.UserResponseDto;
import com.example.uade.tpo.service.OrderService;
import com.example.uade.tpo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/") //Get all orders
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}") //Get order by id
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        Optional<OrderResponseDto> order = orderService.getOrderById(orderId);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping //Create order from cart
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto order) {
        OrderResponseDto newOrder = orderService.createOrder(order);
        if(newOrder == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}") //Delete order
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        Boolean deleted = orderService.deleteOrder(orderId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
