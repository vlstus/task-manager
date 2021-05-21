package com.study.taskmanagement.controller.user;

import com.study.taskmanagement.controller.AbstractRestController;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
class UserRestControllerImpl
        extends AbstractRestController<User, Integer>
        implements UserRestController {

    public UserRestControllerImpl(UserService userService) {
        super(userService);
    }

}
