package com.service.bookverse.feature.order.controller;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.util.SecurityUtil;
import com.service.bookverse.feature.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}