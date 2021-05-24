package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
class UserRestController
        extends AbstractRestController<User, Integer> {

    protected UserRestController(UserService userService) {
        super(userService);
    }

}
