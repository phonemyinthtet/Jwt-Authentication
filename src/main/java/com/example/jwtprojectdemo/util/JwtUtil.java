package com.example.jwtprojectdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY ="we  in myanmar";
    private static final long TOKNE_VALIDITY = 3600 * 5;

    public String getUserNameFromToke(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }
    private <T> T getClaimFromToken(String token , Function<Claims,T>claimsTFunction){
        final var claims = getAllClaimsFromToken(token);
        return claimsTFunction.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public boolean validationToken(String token, UserDetails userDetails){
            var username = getUserNameFromToke(token);
        return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
            var expirationDate = getExpirationDateFromToken(token);
            return expirationDate.before(new Date());
    }
    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetials) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetials.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKNE_VALIDITY *1000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
}
