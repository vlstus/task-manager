package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl
        extends AbstractService<User, Integer>
        implements UserService {

    protected UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Collection<User> getByRole(Role role) {
        UserRepository userRepository = (UserRepository) crudRepository;
        return StreamSupport.stream(userRepository.findAllByRole(role).spliterator(), false)
                .collect(Collectors.toList());
    }

}
