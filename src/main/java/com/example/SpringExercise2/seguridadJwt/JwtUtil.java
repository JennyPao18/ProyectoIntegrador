package com.example.SpringExercise2.seguridadJwt;

import com.example.SpringExercise2.controladores.autorizaciones.TokenDto;
import com.example.SpringExercise2.data.usuarios.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

import static com.example.SpringExercise2.utils.Constants.CLAIMS_ROLES_KEY;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public TokenDto generateToken(String username, List<RoleEnum> roles) {

        Date expirationDate = jwtConfig.getExpirationDate();
        String token = Jwts.builder()
                .setSubject(username) // Usa setSubject en lugar de subject
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim(CLAIMS_ROLES_KEY, roles)
                .signWith(jwtConfig.getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return new TokenDto(token, expirationDate);
    }

    public Claims extractAndVerifyClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

