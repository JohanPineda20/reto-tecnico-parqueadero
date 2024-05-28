package com.nelumbo.parking.infraestructure.security.jwt;

import com.nelumbo.parking.infraestructure.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration-time}")
    private Long expirationTime;

    public String createToken(Authentication auth){

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        Long id = userDetails.getId();
        String email = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("id", id)
                .claim("role", role)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> getClaimFunction) {
        Claims claims = parseToken(token);
        return (claims != null) ? getClaimFunction.apply(claims) : null;
    }
    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
