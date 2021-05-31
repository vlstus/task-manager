package com.study.taskmanagement.controller.view;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.security.JwtTokenProvider;
import com.study.taskmanagement.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @GetMapping()
    public String index(@AuthenticationPrincipal Principal authPrincipal) {
        return authPrincipal == null ? "redirect:login" : "profile";
    }

    @GetMapping(path = "login")
    public String loginPage() {
        return "login";
    }

    @PostMapping(path = "login", params = {"login", "password"})
    public String doLogin(String login, String password, HttpServletResponse response) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(login,
                                password));
        User authenticatedUser = userService.getByName(login);
        String token = tokenProvider.createToken(authenticatedUser.getName(),
                String.valueOf(authenticatedUser.getRole()));

        final Cookie authorizationCookie = new Cookie("Authorization", token);
        authorizationCookie.setHttpOnly(true);
        response.addCookie(authorizationCookie);
        return "redirect:profile";
    }

    @GetMapping("logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:login";
    }

    @GetMapping("profile")
    public String profile() {
        return "profile";
    }

}
