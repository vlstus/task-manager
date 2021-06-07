package com.study.taskmanagement.security;

import com.study.taskmanagement.dto.LoginRequest;
import com.study.taskmanagement.dto.LogoutRequest;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl
        implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;


    public void authenticate(LoginRequest request)
            throws AuthenticationException {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUserName(),
                                request.getPassword()));
        User authenticatedUser = userService.getByName(request.getUserName());
        String token = tokenProvider.createToken(authenticatedUser.getName(),
                String.valueOf(authenticatedUser.getRole()));
        final Cookie authorizationCookie = new Cookie(HttpHeaders.AUTHORIZATION, token);
        authorizationCookie.setHttpOnly(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getResponse().addCookie(authorizationCookie);
    }

    public void unAuthenticate(LogoutRequest request) {
        final Cookie[] cookies = request.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setHttpOnly(true);
                    request.getResponse().addCookie(cookie);
                }
            }
        }
    }

}
