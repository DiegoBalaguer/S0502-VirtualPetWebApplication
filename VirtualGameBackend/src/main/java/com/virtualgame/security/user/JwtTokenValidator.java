package com.virtualgame.security.user;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.virtualgame.security.user.auth.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("Call {} for: {}", this.getClass().getName(), request.getRequestURI());

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.replace("Bearer ", "");

            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            if(decodedJWT != null) {
                String email = decodedJWT.getSubject();
                Long userId = decodedJWT.getClaim("userId").asLong();
                String name = decodedJWT.getClaim("name").asString();
                String languageCode = decodedJWT.getClaim("languageCode").asString();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

                var authorities = AuthorityUtils.createAuthorityList(
                        roles != null ? roles.toArray(new String[0]) : new String[]{}
                );

                SecurityUser securityUser = new SecurityUser(userId, email, name, languageCode);

                Authentication auth = new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
