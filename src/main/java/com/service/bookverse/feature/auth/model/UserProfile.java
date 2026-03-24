package com.service.bookverse.feature.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(unique = true, nullable = false)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER) //This field is a collection of basic types (like String, Enum, Integer), not another entity
    Set<RoleType> roles = new HashSet<RoleType>();

    public UserProfile(String username, String password, Set<RoleType> roles) {
        this.username = username;
        this.password= password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                roleType -> {
                    Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getAuthorities(roleType);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+roleType.name()));
                }
        );

        return authorities;
    }
}
