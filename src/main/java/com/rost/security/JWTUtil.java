package com.rost.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import static java.time.ZonedDateTime.now;

@Component
public class JWTUtil {
    private static final String USERNAME = "username";
    @Value("${jwt.token.subject}")
    private String subject;
    @Value("${jwt.token.issuer}")
    private String issuer;
    @Value("${jwt.token.secret}")
    private String secret;

    public String generateToken(String username) {
        Date expirationDate = Date.from(now().plusMinutes(60).toInstant());
        return JWT.create()
                .withSubject(subject)
                .withClaim(USERNAME, username)
                .withIssuedAt(now().toInstant())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokeAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(USERNAME).asString();
    }
}
