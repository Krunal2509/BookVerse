package com.service.bookverse.feature.auth.util;//package com.service.bookverse.feature.auth.util;

import com.service.bookverse.feature.auth.model.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class SecurityUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getGeneratedToken(UserProfile userProfile){

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .subject(userProfile.getUsername())

                .claims()
                    .add(claims)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000*60*20)) // + 20mins
                .and()

                .signWith(getSecretKey())
                .compact();
    }

    private String extractSubject(String token) {
        return Jwts.parser()
                .verifyWith( getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token, UserProfile userProfile) {
        String username = extractUsernameFromToken(token);
        return (username.equals(userProfile.getUsername())  && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public UserProfile getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserProfile) authentication.getPrincipal();
    }
}
