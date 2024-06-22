package com.example.uade.tpo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.service.ProductService;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get/{adminId}")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@PathVariable Long adminId) {
        List<ProductResponseDto> products = productService.getAllProducts(adminId);
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

    @DeleteMapping("/{productId}/{adminId}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable long productId, @PathVariable long adminId){
        productService.deleteProduct(productId,adminId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable long userId,@PathVariable long productId , @RequestBody ProductRequestDto productDto) {
        
        ProductResponseDto updatedProduct =  productService.updateProduct(userId,productId, productDto);
        

        return new ResponseEntity<>(updatedProduct ,HttpStatus.OK);
    }


}
