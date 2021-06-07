package com.study.taskmanagement.security;

import com.study.taskmanagement.dto.LoginRequest;
import com.study.taskmanagement.dto.LogoutRequest;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    void authenticate(LoginRequest request) throws AuthenticationException;

    void unAuthenticate(LogoutRequest request);

}
