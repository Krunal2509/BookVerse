package com.service.bookverse.feature.cart.service;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.book.model.Book;
import com.service.bookverse.feature.book.repository.BookRepository;
import com.service.bookverse.feature.cart.model.Cart;
import com.service.bookverse.feature.cart.model.CartItem;
import com.service.bookverse.feature.cart.repository.CartItemRepository;
import com.service.bookverse.feature.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private  CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    CartItemRepository cartItemRepository;


    public void addToCart(UserProfile userProfile, Integer bookId, Integer quantity){
        Cart cart = cartRepository.findByUserProfile(userProfile).orElse(Cart.builder()
                        .userProfile(userProfile)
                .build());

        cartRepository.save(cart);


        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));

        CartItem cartItem = cartItemRepository.findByCartAndBook(cart,book);

        if(cartItem != null) cartItem.setQuantity(cartItem.getQuantity() + quantity);
        else{
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setBook(book);
            newItem.setQuantity(quantity);

            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
    }

}
