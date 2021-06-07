package com.study.taskmanagement.security.filter;

import com.study.taskmanagement.security.JwtTokenProvider;
import com.study.taskmanagement.security.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter
        extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);
        try {
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            response.sendError(authenticationException.getHttpStatus().value());
            throw new JwtAuthenticationException("Jwt token is expired or invalid");
        }
        filterChain.doFilter(request, response);
    }

}
