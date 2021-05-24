package com.study.taskmanagement.controller.view.user;

import com.study.taskmanagement.controller.view.AbstractViewResolverController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserViewResolverController
        extends AbstractViewResolverController {

    @GetMapping
    @RequestMapping("/users")
    String users() {
        return "users";
    }

}
