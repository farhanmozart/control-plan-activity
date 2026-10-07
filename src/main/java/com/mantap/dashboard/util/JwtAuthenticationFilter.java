package com.mantap.dashboard.util;

import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.parseToken(token);
            String nip = claims.getSubject();

            Number tokenVersionClaim = claims.get("tokenVersion", Number.class);

            if (tokenVersionClaim == null) {
                SecurityContextHolder.clearContext();
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            long tokenVersion = tokenVersionClaim.longValue();

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsersEntity user = usersRepository.findByNip(nip).orElse(null);

                if (user == null) {
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }

                if (!Boolean.TRUE.equals(user.getIsActive())) {
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }

                long currentTokenVersion = user.getTokenVersion() == null ? 0L : user.getTokenVersion();

                if (currentTokenVersion != tokenVersion) {
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user.getNip(), null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException | IllegalArgumentException exception) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
