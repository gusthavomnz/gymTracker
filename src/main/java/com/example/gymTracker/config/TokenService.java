package com.example.gymTracker.config;

import com.auth0.jwt.JWT; // O IMPORT CORRETO É ESTE
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.gymTracker.model.User;
import org.springframework.stereotype.Service; // Use @Service em vez de @Component para semântica
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    private String secret = "${TOKEN_KEY}";
    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(User user){
        return JWT.create()
                .withClaim("UserId", user.getUserId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(60000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public String validateToken(String token){
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e){
            return "";
        }
    }
}