package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends CrudRepository<User, Integer> {

    User findByName(String name);

}
