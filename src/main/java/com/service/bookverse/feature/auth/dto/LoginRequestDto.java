package com.service.bookverse.feature.auth.dto;

import com.service.bookverse.feature.auth.model.RoleType;
import lombok.Data;

import java.util.Set;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
