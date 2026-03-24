package com.service.bookverse.feature.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {

    //resource : operation
    BOOK_READ("book:read"),
    BOOK_ADD("book:add"),
    BOOK_UPDATE("book:update"),
    BOOK_DELETE("book:delete"),
    CART_MANAGE("cart:manage"),
    ORDER_MANAGE("order:manage");

    private final String permission;
}

