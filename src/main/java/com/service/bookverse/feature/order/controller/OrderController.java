package com.service.bookverse.feature.order.controller;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.util.SecurityUtil;
import com.service.bookverse.feature.order.dto.OrderResponseDto;
import com.service.bookverse.feature.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder() {

        UserProfile user = securityUtil.getLoggedInUser();

        orderService.placeOrder(user);

        return ResponseEntity.ok("Order placed successfully");
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<OrderResponseDto>> getOrders() {

        UserProfile userProfile = securityUtil.getLoggedInUser();

        return ResponseEntity.ok(orderService.getUserOrders(userProfile));
    }

}