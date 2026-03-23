package com.service.bookverse.feature.cart.model;

import com.service.bookverse.feature.book.model.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Book book;

    private Integer quantity;

}
