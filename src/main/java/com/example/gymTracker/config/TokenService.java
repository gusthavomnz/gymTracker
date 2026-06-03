package com.example.gymTracker.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.gymTracker.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${TOKEN_KEY}")
    private String secret;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret);
    }

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