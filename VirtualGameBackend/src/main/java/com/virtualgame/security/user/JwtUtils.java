package com.virtualgame.security.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.virtualgame.security.user.auth.SecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.expiration-ms:86400000}")
    private long jwtExpiration;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;


    public String createToken(Authentication authentication, Long userId, String name) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        String username = authentication.getPrincipal().toString();

        // authorities → lista de strings
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Object principal = authentication.getPrincipal();
        String email;
        if (principal instanceof SecurityUser su) {
            email = su.getEmail();
        } else {
            email = principal.toString();
        }

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(email)
                .withClaim("userId", userId)
                .withClaim("name", name)
                .withClaim("roles", roles)
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(new Date())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()  + jwtExpiration))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);

    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
