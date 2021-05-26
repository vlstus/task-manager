package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends CrudRepository<User, Integer> {

    Optional<User> findByName(String name);

    Iterable<User> findAllByRole(Role role);

}
