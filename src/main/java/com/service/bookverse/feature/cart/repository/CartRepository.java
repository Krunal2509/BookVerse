package com.service.bookverse.feature.cart.repository;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserProfile(UserProfile userProfile);
}
