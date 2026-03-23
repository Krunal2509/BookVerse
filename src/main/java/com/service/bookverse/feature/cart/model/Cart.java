package com.service.bookverse.feature.cart.model;

import com.service.bookverse.feature.auth.model.UserProfile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>();


}
