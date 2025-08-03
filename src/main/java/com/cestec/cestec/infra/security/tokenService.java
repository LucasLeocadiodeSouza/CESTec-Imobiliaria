package com.cestec.cestec.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cestec.cestec.model.securityLogin.sp_user;

@Service
public class tokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generatedToken(sp_user user){
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(user.getLogin())
                            .withExpiresAt(generatedExpirateDate())
                            .sign(algoritimo);
            
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generatedToken ", e);
        }        
    }

    public String getExtractedUsernameFromToken(String token){
        String username = JWT.decode(token).getSubject();
        return username;
    }

    public String validateToken(String token){
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    // lllll
    private Instant generatedExpirateDate(){
        return LocalDateTime.now().plusSeconds(5).toInstant(ZoneOffset.of("-03:00"));
    }
}

