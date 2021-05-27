package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl
        extends AbstractService<User, Integer>
        implements UserService {

    private final PasswordEncoder passwordEncoder;

    protected UserServiceImpl(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collection<User> getByRole(Role role) {
        log.info("Getting all users with role {}", role);
        UserRepository userRepository = (UserRepository) crudRepository;
        return StreamSupport.stream(userRepository.findAllByRole(role).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public User getByName(String name) {
        log.info("Getting user with name {}", name);
        return ((UserRepository) crudRepository).findByName(name)
                .orElseThrow(() -> new BusinessLayerException(String.format("User with %s not found", name)));
    }

    @Override
    public User create(User user) {
        prepareToSave(user);
        return super.create(user);
    }

    @Override
    public User update(User user, Integer id) {
        prepareToSave(user);
        return super.update(user, id);
    }

    private void prepareToSave(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            final User existingUser = crudRepository.findById(user.getId())
                    .orElseThrow(BusinessLayerException::new);
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

}
