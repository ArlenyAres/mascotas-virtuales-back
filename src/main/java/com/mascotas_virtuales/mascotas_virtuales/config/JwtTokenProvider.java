package com.mascotas_virtuales.mascotas_virtuales.config;

import com.mascotas_virtuales.mascotas_virtuales.services.UsuarioService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Lazy UsuarioService usuarioService, @Lazy PasswordEncoder passwordEncoder, @Value("${jwt.validity}") long validityInMilliseconds) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            parser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new JwtException("Invalid or expired JWT token", e);
        }
    }

    public String getUsername(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = usuarioService.loadUserByUsername(username);

        System.out.println("Autenticando al usuario: " + username);
        System.out.println("Roles del usuario: " + userDetails.getAuthorities());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
