package com.study.taskmanagement.controller.view;

import com.study.taskmanagement.dto.LoginRequest;
import com.study.taskmanagement.dto.LogoutRequest;
import com.study.taskmanagement.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

    private final AuthenticationService authenticationService;

    @GetMapping()
    public String index(@AuthenticationPrincipal Principal authPrincipal) {
        return authPrincipal == null ? "redirect:login" : "profile";
    }

    @GetMapping(path = "login")
    public String loginPage() {
        return "login";
    }

    @PostMapping(path = "login",
            params = {
                    "login", "password"
            })
    public String doLogin(String login, String password,
                          HttpServletRequest request, HttpServletResponse response) {
        authenticationService.authenticate(new LoginRequest(login, password, request, response));
        return "redirect:profile";

    }

    @GetMapping("logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.unAuthenticate(new LogoutRequest(request, response));
        return "redirect:login";
    }

    @GetMapping("profile")
    public String profile() {
        return "profile";
    }

}
