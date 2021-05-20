package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class SimpleUserService
        implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        log.info("Creating user {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        log.info("Updating user {}", user);
        if (user.getId() == null) {
            log.warn("Failed to update user {}", user);
            throw new BusinessLayerException();
        }
        return userRepository.save(user);
    }

    @Override
    public User get(Integer id) {
        log.info("Getting user with id {}", id);
        return userRepository.findById(id)
                .orElseThrow(BusinessLayerException::new);
    }

    @Override
    public void delete(User user) {
        log.info("Deleting user {}", user);
        userRepository.delete(user);
    }

}
