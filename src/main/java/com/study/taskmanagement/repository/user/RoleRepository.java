package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository
        extends CrudRepository<Role, Integer> {

    Optional<Role> findByRoleType(Role.Type type);

}
