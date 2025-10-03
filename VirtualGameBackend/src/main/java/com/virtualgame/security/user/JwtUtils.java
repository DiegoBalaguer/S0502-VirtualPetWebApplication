package com.virtualgame.security.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.security.user.auth.SecurityUser;
import com.virtualgame.security.user.auth.dto.AuthCreateTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final AppProperties appProperties;

    public String createToken(AuthCreateTokenDto tokenCreateDot) {
        Algorithm algorithm = Algorithm.HMAC256(appProperties.getSecurityDefaultSecurityJwtPrivateKey());
        //String username = tokenCreateDot.authentication().getPrincipal().toString();

        // authorities → lista de strings
        List<String> roles = tokenCreateDot.authentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Object principal = tokenCreateDot.authentication().getPrincipal();
        String email;
        if (principal instanceof SecurityUser su) {
            email = su.getEmail();
        } else {
            email = principal.toString();
        }

        return JWT.create()
                .withIssuer(appProperties.getSecurityDefaultSecurityJwtGeneratorUser())
                .withSubject(email)
                .withClaim("userId", tokenCreateDot.userId())
                .withClaim("name", tokenCreateDot.userName())
                .withClaim("languageCode", tokenCreateDot.userLanguage())
                .withClaim("roles", roles)
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(new Date())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()  + appProperties.getSecurityDefaultSecurityJwtExpiration()))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(appProperties.getSecurityDefaultSecurityJwtPrivateKey());

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(appProperties.getSecurityDefaultSecurityJwtGeneratorUser())
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);

    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
