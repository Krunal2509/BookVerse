package com.service.bookverse.feature.auth.service;

import com.service.bookverse.config.security.AppConfig;
import com.service.bookverse.feature.auth.dto.LoginRequestDto;
import com.service.bookverse.feature.auth.dto.LoginResponseDto;
import com.service.bookverse.feature.auth.dto.RegisterRequestDto;
import com.service.bookverse.feature.auth.dto.RegisterResponseDto;
import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AppConfig appConfig;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<RegisterResponseDto> register(RegisterRequestDto req) {

        UserProfile userProfile = userRepository.getUserProfileByUsername(req.getUsername()).orElse(null);

        if(userProfile != null) throw new IllegalArgumentException("User Already Exists");

        userRepository.save(new UserProfile(
                                req.getUsername(),
                                appConfig.getBcryptPasswordEncoder().encode(req.getPassword()),
                                req.getRoles()));

        return new ResponseEntity<>(new RegisterResponseDto(req.getUsername()),HttpStatus.OK);
    }

    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request ){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if(authentication.isAuthenticated()){
            UserProfile userProfile = (UserProfile) authentication.getPrincipal();

            LoginResponseDto loginResponseDto = new LoginResponseDto(userProfile.getId());

            return new ResponseEntity<>(loginResponseDto ,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
