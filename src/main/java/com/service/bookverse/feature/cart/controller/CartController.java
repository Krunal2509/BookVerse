package com.service.bookverse.feature.cart.controller;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.util.SecurityUtil;
import com.service.bookverse.feature.cart.dto.CartResponseDto;
import com.service.bookverse.feature.cart.service.CartService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    SecurityUtil securityUtil;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam("bookId") Integer bookId, @RequestParam("quantity") Integer quantity){
        UserProfile userProfile = securityUtil.getLoggedInUser();

        cartService.addToCart(userProfile, bookId, quantity);

        return new ResponseEntity<>("Item Added To Cart", HttpStatus.OK);
    }

    @GetMapping("/getCart")
    public ResponseEntity<CartResponseDto> getCart() {

        UserProfile userProfile = securityUtil.getLoggedInUser();

        CartResponseDto response = cartService.getCart(userProfile);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponseDto> updateItem(@RequestParam Integer bookId,
                                                      @RequestParam Integer quantity) {

        UserProfile userProfile = securityUtil.getLoggedInUser();

        return ResponseEntity.ok(cartService.updateCartItem(userProfile, bookId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartResponseDto> removeItem(@RequestParam("bookId") Integer bookId) {

        UserProfile userProfile = securityUtil.getLoggedInUser();

        return ResponseEntity.ok(cartService.removeItem(userProfile, bookId));
    }
}
