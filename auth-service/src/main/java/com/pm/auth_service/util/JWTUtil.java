package com.pm.auth_service.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey generateKey(){
        byte[] decode= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String generateToken(String email, String role){
        return Jwts
                .builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*20)) // 20 min
                .signWith(generateKey())
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith((SecretKey) generateKey())
                    .build()
                    .parseSignedClaims(token);
        }
        catch (SignatureException e){
            throw new JwtException("Invalid JWT Signature");
        }
        catch(JwtException e){
            throw new JwtException("Invalid JWT");
        }
    }
}
