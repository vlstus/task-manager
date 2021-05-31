package com.study.taskmanagement.controller.rest.security;

import com.study.taskmanagement.dto.LoginRequest;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.security.JwtTokenProvider;
import com.study.taskmanagement.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tokens")
public class SecurityRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(request.getUserName(),
                                    request.getPassword()));
            User authenticatedUser = userService.getByName(request.getUserName());
            String token = tokenProvider.createToken(authenticatedUser.getName(),
                    String.valueOf(authenticatedUser.getRole()));
            Map<Object, Object> responseBody = new HashMap<>();
            responseBody.put("id", authenticatedUser.getId());
            responseBody.put("username", authenticatedUser.getName());
            responseBody.put("token", token);

            final Cookie authorizationCookie = new Cookie("Authorization", token);
            authorizationCookie.setHttpOnly(true);
            response.addCookie(authorizationCookie);
            return ResponseEntity.ok(responseBody);
        } catch (AuthenticationException authenticationException) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }
    }

}
