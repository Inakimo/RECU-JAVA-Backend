package com.example.uade.tpo.service;

import com.example.uade.tpo.Utils.Mapper;
import com.example.uade.tpo.dtos.request.DiscountRequestDto;
import com.example.uade.tpo.dtos.request.DiscountUpdateRequestDto;
import com.example.uade.tpo.dtos.response.DiscountResponseDto;
import com.example.uade.tpo.entity.Discount;
import com.example.uade.tpo.repository.IDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private IDiscountRepository discountRepository;

    public List<DiscountResponseDto> getAllDiscounts() {
        return discountRepository.findAll().stream().map
                (Mapper::convertToDiscountResponseDto).collect(Collectors.toList());
    }

    public DiscountResponseDto createDiscount(DiscountRequestDto discountDto) {
        Discount discount = new Discount();
        List<Discount> discounts = discountRepository.findAll();
        for (Discount d : discounts) {
            if (d.getCode().equals(discountDto.getCode())) {
                return null;
            }
        }
        discount.setCode(discountDto.getCode());
        discount.setPercentage(discountDto.getPercentage());
        discount.setStartDate(discountDto.getStartDate());
        discount.setEndDate(discountDto.getEndDate());
        discount.setActive(discountDto.getActive());
        return Mapper.convertToDiscountResponseDto(discountRepository.save(discount));
    }

    public DiscountResponseDto updateDiscount(Long discountId, DiscountUpdateRequestDto discountDetails) {
        Optional<Discount> discountOptional = discountRepository.findById(discountId);
        if (discountOptional.isPresent()) {
            Discount discount = new Discount();
            discount.setId(discountOptional.get().getId());
            discount.setCode(discountOptional.get().getCode());
            discount.setPercentage(discountDetails.getPercentage());
            discount.setStartDate(discountDetails.getStartDate());
            discount.setEndDate(discountDetails.getEndDate());
            discount.setActive(discountDetails.getActive());
            return Mapper.convertToDiscountResponseDto(discountRepository.save(discount));
        }
        return null;
    }

    public Boolean deleteDiscount(Long discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
            return true;
        }
        return false;
    }
}
