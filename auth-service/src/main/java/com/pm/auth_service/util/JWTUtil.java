package com.pm.auth_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private ObjectMapper objectMapper;

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

    public void validateToken(String token, String role) {
        try {
            Jwts
                    .parser()
                    .verifyWith((SecretKey) generateKey())
                    .build()
                    .parseSignedClaims(token);

            String[] chunks=token.split("\\.");
            if(chunks.length!=3) throw new JwtException("Invalid JWT");

            Base64.Decoder decoder=Base64.getUrlDecoder();

            String payloadEncoded=chunks[1];
            String payload=new String(decoder.decode(payloadEncoded));

            Map<String, String> payloadMap=objectMapper.readValue(payload, Map.class);

            log.info("[ ROLE ]: {}", payloadMap.get("role"));

            if(!payloadMap.get("role").equalsIgnoreCase(role)) throw new JwtException("Invalid JWT");
        }
        catch (SignatureException e){
            log.info(e.getMessage());
            throw new JwtException("Invalid JWT Signature");
        }
        catch(JwtException e){
            log.info(e.getMessage());
            throw new JwtException("Invalid JWT");
        }
        catch(Exception e){
            throw new JwtException("Invalid JWT");
        }
    }
}
