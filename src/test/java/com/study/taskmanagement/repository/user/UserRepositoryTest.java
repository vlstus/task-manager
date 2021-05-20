package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.study.taskmanagement.repository.user.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;


class UserRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

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
        final User admin = new User("Admin", "Password", RoleTestData.ADMIN_ROLE);
        assertThat(userRepository.save(admin))
                .extracting(User::getName)
                .isEqualTo(admin.getName());
    }

    @Test
    void update() {
        final User updatedDeveloper = UserTestData.copyOf(TEST_DEVELOPER);
        final String updatedName = "updated";
        updatedDeveloper.setName(updatedName);
        assertThat(userRepository.save(updatedDeveloper))
                .extracting(User::getName)
                .isEqualTo(updatedName);
    }

    @Test
    void delete() {
        userRepository.deleteById(TEST_MANAGER_ID);
        assertThat(userRepository.findById(TEST_MANAGER_ID))
                .isEmpty();
    }

}