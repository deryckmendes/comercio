package com.comercio.comercio_auth.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.comercio.comercio_auth.user.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(User user) {
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withClaim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList())
                    .withExpiresAt(LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00")))
                    .sign(getAlgorithm());

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating access token", exception);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00")))
                    .sign(getAlgorithm());

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating refresh token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Expired or invalid token", exception);
        }
    }
}
