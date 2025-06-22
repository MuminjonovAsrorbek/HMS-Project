package uz.dev.hmsproject.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:15
 **/

@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${myapp.secret-key}")
    private String SECRET_KEY;

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .claim("role", "USER")
                .claim("randomId", UUID.randomUUID().toString())
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();

    }

    public String parseToken(String token) {

        DefaultClaims payload = (DefaultClaims) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parse(token)
                .getPayload();

        return payload.getSubject();
    }

}
