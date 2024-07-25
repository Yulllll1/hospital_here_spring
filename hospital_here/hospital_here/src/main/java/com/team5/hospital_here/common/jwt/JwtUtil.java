package com.team5.hospital_here.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.access-secret}")
    private String accessSecretKey;
    @Value("${jwt.refresh-secret}")
    private String refreshSecretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.role}")
    private String role;

    public final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    public final int REFRESH_TOKEN_COOKIE_MAX_AGE = 60 * 60 * 24 *7;
    public final Long REFRESH_TOKEN_EXPIRE_TIME_MS = 1000L * 60 * 60 * 24 * 7;

    public String generateAccessToken(String email){
        return generateToken(email, accessSecretKey, expiration);
    }

    public String generateRefreshToken(String email){
        return generateToken(email, refreshSecretKey, REFRESH_TOKEN_EXPIRE_TIME_MS);
    }

    private String generateToken(String email, String secretKey, long expireTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getEmailFromAccessToken(String token){
        return getEmailFromToken(token, accessSecretKey);
    }

    public String getEmailFromRefreshToken(String token){
        return getEmailFromToken(token, refreshSecretKey);
    }

    private String getEmailFromToken(String token, String secretKey) {
        return getClaimsFromToken(token, secretKey).getSubject();
    }

    private Claims getClaimsFromToken(String token, String secretKey){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public boolean validateAccessToken(String token){
        return validateToken(token, accessSecretKey);
    }

    public boolean validateRefreshToken(String token){
        return validateToken(token, refreshSecretKey);
    }

    public boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenToBearerToken(String bearerToken){
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return bearerToken;
    }
}
