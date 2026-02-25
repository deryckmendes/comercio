package com.comercio.comercio_catalogo.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

        public DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);
                    // .getSubject()
        } catch (JWTVerificationException exception) {
            System.out.println("ERROR: " + exception);
            return null;
        }
    }
}
