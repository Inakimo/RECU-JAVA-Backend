package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.entity.Category;
import com.example.uade.tpo.entity.Product;
import com.example.uade.tpo.entity.Role;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.ICategoryRepository;
import com.example.uade.tpo.repository.IProductRepository;
import com.example.uade.tpo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map
                (Mapper::convertToProductResponseDto).collect(Collectors.toList());
    }

    public ProductResponseDto createProduct(Long userId, ProductRequestDto productDto) {
        Product product = new Product();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setBrand(productDto.getBrand());
            product.setPrice(productDto.getPrice());
            product.setStock(productDto.getStock());
            if(!user.get().getRole().equals(Role.ROLE_SELLER)){
                user.get().setRole(Role.ROLE_SELLER);
            }
            product.setSeller(user.get());

            List<Category> categories = new ArrayList<>();
            for(Long categoryId : productDto.getCategoryIds()){
                Optional<Category> category = categoryRepository.findById(categoryId);
                category.ifPresent(categories::add);
            }
            product.setCategories(categories);
            Product savedProduct = productRepository.save(product);
            return Mapper.convertToProductResponseDto(savedProduct);
        }
        return null; //No se encontro usuario con ese id
    }


}
