package com.example.Smart.Management.System.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET =
            "mysecretkeymysecretkeymysecretkey123";

    public Key getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

    // ✅ GENERATE TOKEN
    public String generateToken(
            String email,
            String role,
            String name
    ) {

        return Jwts.builder()

                .setSubject(email)

                .claim("role", role)

                // ✅ ADD NAME
                .claim("name", name)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(
                        getSignKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    // ✅ EXTRACT CLAIMS
    public Claims extractClaims(
            String token
    ) {

        return Jwts.parserBuilder()

                .setSigningKey(
                        getSignKey()
                )

                .build()

                .parseClaimsJws(token)

                .getBody();
    }
}