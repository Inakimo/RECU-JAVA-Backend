package com.example.uade.tpo.controller;

import com.example.uade.tpo.dtos.request.DiscountRequestDto;
import com.example.uade.tpo.dtos.request.DiscountUpdateRequestDto;
import com.example.uade.tpo.dtos.response.DiscountResponseDto;
import com.example.uade.tpo.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping() //Get all discounts
    public ResponseEntity<List<DiscountResponseDto>> getAllDiscounts() {
        List<DiscountResponseDto> discounts = discountService.getAllDiscounts();
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @PostMapping //Create discount
    public ResponseEntity<?> createDiscount(@RequestBody DiscountRequestDto discount) {
        DiscountResponseDto newDiscount = discountService.createDiscount(discount);
        if(newDiscount == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newDiscount, HttpStatus.CREATED);
    }

    @PutMapping("/{discountId}") //Update discount
    public ResponseEntity<?> updateDiscount
            (@PathVariable Long discountId,@RequestBody @Valid DiscountUpdateRequestDto discount) {
        DiscountResponseDto updatedDiscount = discountService.updateDiscount(discountId, discount);
        if(updatedDiscount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDiscount, HttpStatus.OK);
    }

    @DeleteMapping("/{discountId}") //Delete discount
    public ResponseEntity<?> deleteDiscount(@PathVariable Long discountId) {
        Boolean deleted = discountService.deleteDiscount(discountId);
        if(!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
