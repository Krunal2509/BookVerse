package com.service.bookverse.feature.cart.repository;

import com.service.bookverse.feature.book.model.Book;
import com.service.bookverse.feature.cart.model.Cart;
import com.service.bookverse.feature.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndBook(Cart cart, Book book);

}
