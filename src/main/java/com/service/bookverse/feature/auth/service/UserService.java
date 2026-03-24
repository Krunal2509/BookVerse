package com.service.bookverse.feature.auth.service;

import com.service.bookverse.config.security.AppConfig;
import com.service.bookverse.feature.auth.dto.LoginRequestDto;
import com.service.bookverse.feature.auth.dto.LoginResponseDto;
import com.service.bookverse.feature.auth.dto.RegisterRequestDto;
import com.service.bookverse.feature.auth.dto.RegisterResponseDto;
import com.service.bookverse.feature.auth.model.RoleType;
import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.repository.UserRepository;
import com.service.bookverse.feature.auth.util.SecurityUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AppConfig appConfig;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<RegisterResponseDto> register(RegisterRequestDto req) {

        if (userRepository.getUserProfileByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User Already Exists");
        }

        Set<RoleType> roles = (req.getRoles() != null) ? new HashSet<>(req.getRoles()) : new HashSet<>();

        if (roles.isEmpty()) {
            roles.add(RoleType.BUYER);
        }

        UserProfile user = new UserProfile(
                req.getUsername(),
                appConfig.getBcryptPasswordEncoder().encode(req.getPassword()),
                roles
        );

        userRepository.save(user);

        return new ResponseEntity<>(
                new RegisterResponseDto(user.getUsername()),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<LoginResponseDto> login(@NonNull LoginRequestDto request ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if(authentication.isAuthenticated()){
            UserProfile userProfile = (UserProfile) authentication.getPrincipal();

            if(userProfile == null) throw new IllegalArgumentException("UserProfile Is NULL");

            String jwtToken = securityUtil.getGeneratedToken(userProfile);

            LoginResponseDto loginResponseDto = new LoginResponseDto(userProfile.getId(), jwtToken);

            return new ResponseEntity<>(loginResponseDto ,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
