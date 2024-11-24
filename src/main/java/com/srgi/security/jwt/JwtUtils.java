package com.srgi.security.jwt;

import com.srgi.security.usuario.ReqUsuarioDetalle;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int jwtExpiration;

    public String generarTokenParaUsuario(Authentication authentication) {
        ReqUsuarioDetalle usuarioPrincipal = (ReqUsuarioDetalle) authentication.getPrincipal();

        List<String> roles = usuarioPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        //Encodea la informacion del usuario que quieras, mediante el claim
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getEmail())
                .claim("id", usuarioPrincipal.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(key(), SignatureAlgorithm.ES256).compact();
    }

    private Key key(){return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));}


    //Extraemos el usuario/email del token que recibimos
    public String getUsuarioDelToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validarToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                IllegalArgumentException exception){
            throw new JwtException(exception.getMessage());
        }
    }
}
