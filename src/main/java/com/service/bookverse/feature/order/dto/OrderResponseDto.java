package com.service.bookverse.feature.order.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderResponseDto {

    private Integer orderId;
    private List<OrderItemDto> items;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;

}