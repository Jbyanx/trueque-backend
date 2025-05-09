package com.ingsoft.trueque.service.impl.security;

import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.repository.PersonaRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final PersonaRepository personaRepository;
    @Value("${security.jwt.expiracion-en-minutos}")
    private Long EXPIRATION_EN_MINUTOS;
    @Value("${security.jwt.secret-key}")
    private String  SECRET_KEY;

    public String generarToken(UserDetails persona, Map<String, Object> claims) {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + (EXPIRATION_EN_MINUTOS * 60 * 1000));

        String token = Jwts.builder()
                .header()
                    .type("JWT")
                .and()
                .subject(persona.getUsername()) // en realidad ese metodo setea el correo
                .issuedAt(now)
                .expiration(expiration)
                .claims(claims)

                .signWith(generarKey(), Jwts.SIG.HS256)
                .compact();
        return token;
    }

    private SecretKey generarKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String extractSessionId(String token) {
        return extractAllClaims(token).get("sessionId").toString();
    }

    public String extractCorreo(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generarKey()).build()
                .parseClaimsJws(token).getPayload();
    }

    public Boolean isvalid(String token, UserDetails persona) {
        String correo = extractCorreo(token);
        String sessionIdFromToken = extractSessionId(token);

        Persona p = personaRepository.findByCorreoEqualsIgnoreCase(correo)
                .orElseThrow(() -> new UsuarioNotFoundException("Persona no encontrada"));

        return correo.equals(persona.getUsername()) && sessionIdFromToken.equals(p.getSessionId());
    }

}
