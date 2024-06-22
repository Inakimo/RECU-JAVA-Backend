package com.example.uade.tpo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.ProductRequestDto;
import com.example.uade.tpo.dtos.response.ProductResponseDto;
import com.example.uade.tpo.entity.Category;
import com.example.uade.tpo.entity.Product;
import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.repository.ICategoryRepository;
import com.example.uade.tpo.repository.IProductRepository;
import com.example.uade.tpo.repository.IUserRepository;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ICategoryRepository categoryRepository;

    public List<ProductResponseDto> getAllProducts(long adminId) {

        if(!userService.isAdmin(adminId)){
            return null;
        }
        return productRepository.findAll().stream().map
                (Mapper::convertToProductResponseDto).collect(Collectors.toList());
    }

    public ProductResponseDto createProduct(Long userId, ProductRequestDto productDto) {
        Product product = new Product();
        Optional<User> user = userRepository.findById(userId);
        
        if(!userService.isAdmin(userId) && !userService.isSeller(userId) ){
            return null;
        }
        
        if(user.isPresent()){
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setBrand(productDto.getBrand());
            product.setPrice(productDto.getPrice());
            product.setStock(productDto.getStock());
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

    public void deleteProduct(Long productId,Long adminId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(userService.isAdmin(adminId)){
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                productRepository.delete(product);
            }
        }

        
    }

    
    public ProductResponseDto updateProduct(Long userId,Long productId, ProductRequestDto productDetails){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (userService.isAdmin(userId)) {
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setName(productDetails.getName());
                product.setDescription(productDetails.getDescription());
                product.setBrand(productDetails.getBrand());
                product.setPrice(productDetails.getPrice());
                Product updatedProduct = productRepository.save(product);
    
                return Mapper.convertToProductResponseDto(updatedProduct);
            
        }
    } return null;

}

}



