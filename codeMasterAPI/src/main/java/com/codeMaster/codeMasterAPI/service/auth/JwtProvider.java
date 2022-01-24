package com.codeMaster.codeMasterAPI.service.auth;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.*;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Data
@Configuration
@Slf4j
public class JwtProvider {

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationSeconds;

    @Autowired
    private KeyStoreManager keyStoreManager;

    public String generateToken(Authentication authenticate) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        User user = (User) authenticate.getPrincipal();
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user.getUsername());
        builder.setExpiration(Date.from(Instant.now().plusSeconds(jwtExpirationSeconds)));
        builder.signWith(SignatureAlgorithm.RS256,getPrivateKey());
        builder.setIssuedAt(Date.from(Instant.now()));
        String jwt = builder.compact();
        return jwt;

    }


    public String generateTokenWithEmail(String email) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(email);
        builder.setExpiration(Date.from(Instant.now().plusSeconds(jwtExpirationSeconds)));
        builder.signWith(SignatureAlgorithm.RS256,(PrivateKey)getPrivateKey());
        builder.setIssuedAt(Date.from(Instant.now()));
        return builder.compact();
    }

    public boolean validateToken(String jwt){
        try {
            parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    private PublicKey getPublicKey() {
        return keyStoreManager.getPublicKey("montooboss");
    }

    private Key getPrivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {

        return keyStoreManager.getPrivateKey("montooboss", "1234@1234");
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
