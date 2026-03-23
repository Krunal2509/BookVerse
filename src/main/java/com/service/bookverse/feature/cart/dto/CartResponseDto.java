package com.service.bookverse.feature.cart.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponseDto {
    private List<CartItemDto> items = new ArrayList<>();
    private Double grandTotal;

}
