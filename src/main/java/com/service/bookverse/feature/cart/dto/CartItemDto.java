package com.service.bookverse.feature.cart.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Integer bookId;
    private String title;
    private Integer quantity;
    private Integer price;
    private Double total;
}
