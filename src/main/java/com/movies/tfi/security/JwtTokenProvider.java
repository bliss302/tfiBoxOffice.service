package com.movies.tfi.security;

import com.movies.tfi.exception.AppApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //generate jwt token
    public String getJwtToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key())
                .compact();

        return token;
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    //get username from token
    public String getUsername(String token){
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token) throws AppApiException {
        try {
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException e){
            throw new AppApiException(HttpStatus.BAD_REQUEST,"Expired JWT token");
        }catch (UnsupportedJwtException e){
            throw new AppApiException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        }catch (IllegalArgumentException e){
            throw new AppApiException(HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }

    }
}
