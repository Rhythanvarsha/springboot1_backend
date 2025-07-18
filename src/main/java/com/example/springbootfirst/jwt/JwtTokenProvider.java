package com.example.springbootfirst.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMilliSeconds;


    private Key secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    //    public String generateToken(Authentication authenticate){
//        UserDetails userPrincipal= (UserDetails) authenticate.getPrincipal();
//        return Jwts.builder()              //to generate json web token ,built and compact it
//                .setSubject(userPrincipal.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMilliSeconds))
//                .signWith(secretKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
    public String generateToken(Authentication authenticate) {
        UserDetails userPrincipal = (UserDetails) authenticate.getPrincipal();

        // Extract roles/authorities
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Add roles to the claims
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)  // 👈 include roles in token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMilliSeconds))
                .signWith(secretKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    //to get username from token
    public  String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }




    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }

}
