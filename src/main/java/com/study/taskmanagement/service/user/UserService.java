package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.User;

public interface UserService {

    User create(User user);

    User update(User user);

    void delete(User user);

    User get(Integer id);

}
