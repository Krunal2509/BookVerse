package com.service.bookverse.feature.auth.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RolePermissionMapping {
    private static final Map<RoleType, Set<PermissionType>> map = Map.of(

            RoleType.ADMIN, Set.of(
                    PermissionType.BOOK_READ,
                    PermissionType.BOOK_ADD,
                    PermissionType.BOOK_UPDATE,
                    PermissionType.BOOK_DELETE
            ),

            RoleType.BUYER, Set.of(
                    PermissionType.BOOK_READ,
                    PermissionType.CART_MANAGE,
                    PermissionType.ORDER_MANAGE
            ),

            RoleType.SELLER, Set.of(
                    PermissionType.BOOK_ADD,
                    PermissionType.BOOK_UPDATE,
                    PermissionType.BOOK_DELETE
            )
    );

    public static Set<SimpleGrantedAuthority> getAuthorities(RoleType role){
        return map.get(role).stream().
                map(permissionType -> new SimpleGrantedAuthority(permissionType.name())).
                collect(Collectors.toSet());
    }
}
