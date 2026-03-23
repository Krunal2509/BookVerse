package com.service.bookverse.feature.cart.repository;

import com.service.bookverse.feature.book.model.Book;
import com.service.bookverse.feature.cart.model.Cart;
import com.service.bookverse.feature.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemRepository, Integer> {
    CartItem findByCartAndBook(Cart cart, Book book);
}
