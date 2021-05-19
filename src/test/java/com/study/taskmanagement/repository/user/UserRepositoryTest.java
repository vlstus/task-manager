package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import com.study.taskmanagement.repository.exception.RepositoryLayerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.study.taskmanagement.repository.user.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;


class UserRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    void findAll() {
        assertThat(userRepository.findAll())
                .isNotEmpty()
                .hasSize(TEST_USERS.size());
    }

    @Test
    void findById() {
        assertThat(userRepository.findById(TEST_DEVELOPER_ID))
                .isNotEmpty()
                .hasValueSatisfying(user ->
                        assertThat(user)
                                .usingRecursiveComparison()
                                .ignoringFields("id", "password", "role.id")
                                .isEqualTo(TEST_DEVELOPER));
    }

    @Test
    void create() {
        final User admin = getNew();
        final Integer adminRoleId = getRoleId(admin.getRole());
        admin.getRole().setId(adminRoleId);
        assertThat(userRepository.save(admin))
                .extracting(User::getName)
                .isEqualTo(admin.getName());
    }

    @Test
    void update() {
        final User updatedDeveloper = getUpdatedDeveloper();
        updatedDeveloper.getRole().setId(getRoleId(updatedDeveloper.getRole()));
        assertThat(userRepository.save(updatedDeveloper))
                .extracting(User::getName)
                .isEqualTo(UPDATED_NAME);
    }

    @Test
    void delete() {
        userRepository.deleteById(TEST_MANAGER_ID);
        assertThat(userRepository.findById(TEST_MANAGER_ID))
                .isEmpty();
    }


    private int getRoleId(Role role) {
        return roleRepository.findByRoleType(role.getRoleType())
                .orElseThrow(RepositoryLayerException::new).getId();
    }

}