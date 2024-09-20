package com.enigmacamp.loan_app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigmacamp.loan_app.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    @Value("${app.loan-app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.loan-app.app-name}")
    private String appName;

    @Value("${app.loan-app.jwtExpirationInSecond}")
    private long jwtExpirationInSecond;

    public String generateToken(AppUser appUser){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            log.info("Token generated");
            String token =  JWT.create()
                    .withIssuer(appName)
                    .withSubject(appUser.getId())
                    .withExpiresAt(Instant.now().plusSeconds(3600000))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", appUser.getRole().name())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            log.error("Exception while generating token: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes()); // hanya untuk mengubah character menjadi bytes
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException e) {
            log.error("Invalid verification JWT: " + e.getMessage());
            return false;
        }
    }

    public Map<String, String> getUserInfoByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes()); // hanya untuk mengubah character menjadi bytes
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        } catch (JWTVerificationException e) {
            log.error("Invalid verification JWT: " + e.getMessage());
            return null;
        }
}}
