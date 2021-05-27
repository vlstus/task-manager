package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.CrudService;

import java.util.Collection;

public interface UserService
        extends CrudService<User, Integer> {

    Collection<User> getByRole(Role role);

    User getByName(String name);

}
