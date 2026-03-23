package com.service.bookverse.feature.order.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Integer bookId;
    private String title;
    private Integer quantity;
    private Integer price;
    private Double total;

}