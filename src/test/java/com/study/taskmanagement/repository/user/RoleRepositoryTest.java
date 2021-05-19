package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RoleRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void getBy() {
        assertThat(roleRepository.findByRoleType(Role.Type.ADMIN))
                .isPresent()
                .hasValueSatisfying(role -> assertThat(role.getRoleType())
                        .isEqualTo(Role.ofType("ADMIN").getRoleType()));
    }

}