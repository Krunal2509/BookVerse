package com.service.bookverse.feature.auth.dto;

import com.service.bookverse.feature.auth.model.RoleType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data

public class RegisterRequestDto {
    private String username;
    private String password;

    private Set<RoleType> roles = new HashSet<>();

}
