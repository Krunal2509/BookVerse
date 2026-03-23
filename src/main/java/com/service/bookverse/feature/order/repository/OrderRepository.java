package com.service.bookverse.feature.order.repository;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserProfile(UserProfile userProfile);
}