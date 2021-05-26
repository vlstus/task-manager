package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
class UserRestController
        extends AbstractRestController<User, Integer> {

    protected UserRestController(UserService userService) {
        super(userService);
    }

    @GetMapping(params = {"role"})
    public List<User> getByRole(@RequestParam("role") Role role) {
        UserService userService = (UserService) crudService;
        return new ArrayList<>(userService.getByRole(role));
    }

}
