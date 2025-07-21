package com.barbirms.hw4_t1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final String SECRET = "00secret1234567891011121314" +
            "15secret00secret098123452100secret12345678910" +
            "1112131415secret00secret0981234521";

    private final int EXPIRE = 1500000;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE))
                .signWith(myKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key myKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(myKey()).build().parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(myKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
