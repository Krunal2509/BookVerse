package com.service.bookverse.feature.auth.filter;

import com.service.bookverse.feature.auth.model.UserProfile;
import com.service.bookverse.feature.auth.repository.UserRepository;
import com.service.bookverse.feature.auth.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    SecurityUtil securityUtil;
;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return; // important
        }

        String token = header.split("Bearer ")[1];

        String username = securityUtil.extractUsernameFromToken(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserProfile userProfile = userRepository.findByUsername(username);

            if(securityUtil.isValidToken(token, userProfile)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userProfile, null, userProfile.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
