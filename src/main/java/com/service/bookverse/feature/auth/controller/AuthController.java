package com.service.bookverse.feature.auth.controller;

import com.service.bookverse.feature.auth.dto.LoginRequestDto;
import com.service.bookverse.feature.auth.dto.LoginResponseDto;
import com.service.bookverse.feature.auth.dto.RegisterRequestDto;
import com.service.bookverse.feature.auth.dto.RegisterResponseDto;
import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return userService.register(registerRequestDto);
    }




}
