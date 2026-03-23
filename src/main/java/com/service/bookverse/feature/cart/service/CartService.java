package com.service.bookverse.feature.cart.service;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.book.model.Book;
import com.service.bookverse.feature.book.repository.BookRepository;
import com.service.bookverse.feature.cart.dto.CartItemDto;
import com.service.bookverse.feature.cart.dto.CartResponseDto;
import com.service.bookverse.feature.cart.model.Cart;
import com.service.bookverse.feature.cart.model.CartItem;
import com.service.bookverse.feature.cart.repository.CartItemRepository;
import com.service.bookverse.feature.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private  CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    CartItemRepository cartItemRepository;


    public void addToCart(UserProfile userProfile, Integer bookId, Integer quantity){
        Cart cart = cartRepository.findByUserProfile(userProfile).orElse(
                cartRepository.save(
                        Cart.builder()
                                .userProfile(userProfile)
                            .build()
                )
        );

        cartRepository.save(cart);


        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));

        CartItem cartItem = cartItemRepository.findByCartAndBook(cart,book);

        if(cartItem != null) cartItem.setQuantity(cartItem.getQuantity() + quantity);
        else{
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setBook(book);
            newItem.setQuantity(quantity);

            cartItemRepository.save(newItem);
        }

    }


    public CartResponseDto getCart(UserProfile userProfile) {

        Cart cart = cartRepository.findByUserProfile(userProfile)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserProfile(userProfile);
                    return cartRepository.save(newCart);
                });

        List<CartItemDto> itemDtos = new ArrayList<>();
        double grandTotal = 0;

        for (CartItem item : cart.getItems()) {

            CartItemDto dto = new CartItemDto();
            dto.setBookId(item.getBook().getId());
            dto.setTitle(item.getBook().getTitle());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getBook().getPrice());

            double total = (double) item.getQuantity() * item.getBook().getPrice();
            dto.setTotal(total);

            grandTotal += total;

            itemDtos.add(dto);
        }

        CartResponseDto response = new CartResponseDto();
        response.setItems(itemDtos);
        response.setGrandTotal(grandTotal);

        return response;
    }


}
