package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/{userId}") //Create product
    public ResponseEntity<ProductResponseDto> createProduct(@PathVariable Long userId, @RequestBody ProductRequestDto productDto) {
        ProductResponseDto newProduct = productService.createProduct(userId, productDto);
        if (newProduct == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

}
