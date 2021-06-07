package com.study.taskmanagement.controller.rest.security;

import com.study.taskmanagement.dto.LoginRequest;
import com.study.taskmanagement.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tokens")
public class SecurityRestController {

    private final AuthenticationService authenticationService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        request.setRequest(httpRequest);
        request.setResponse(httpResponse);
        authenticationService.authenticate(request);
        return ResponseEntity.ok().build();
    }

}
