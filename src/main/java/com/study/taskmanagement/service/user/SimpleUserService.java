package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserService
        extends AbstractService<User, Integer>
        implements UserService {

    protected SimpleUserService(UserRepository userRepository) {
        super(userRepository);
    }

}
