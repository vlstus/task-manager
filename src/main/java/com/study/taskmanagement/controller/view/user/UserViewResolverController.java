package com.study.taskmanagement.controller.view.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserViewResolverController {

    @GetMapping
    @RequestMapping("/users")
    String users() {
        return "users";
    }

}
