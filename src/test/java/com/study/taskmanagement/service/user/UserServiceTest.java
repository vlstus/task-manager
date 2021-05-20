package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.BaseServiceTest;
import com.study.taskmanagement.service.exception.BusinessLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.study.taskmanagement.repository.user.UserTestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockBean(UserRepository.class)
class UserServiceTest
        extends BaseServiceTest {

    @Autowired
    UserRepository mockRepository;
    @Autowired
    UserService userService;

    @BeforeEach
    void setUpMock() {
        when(mockRepository.findById(TEST_DEVELOPER_ID))
                .thenReturn(Optional.of(copyOf(TEST_DEVELOPER)));
        when(mockRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void create() {
        User newUser = new User("New", "Pass", RoleTestData.ADMIN_ROLE);
        assertThat(userService.create(newUser))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newUser);
    }

    @Test
    void update() {
        final User manager = copyOf(TEST_MANAGER);
        final String updatedName = "Updated";
        manager.setName(updatedName);
        assertThat(userService.update(manager))
                .hasFieldOrPropertyWithValue("name", updatedName);
    }

    @Test
    void delete() {
        assertThatCode(() -> userService.delete(TEST_MANAGER))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> userService.get(TEST_MANAGER_ID))
                .isInstanceOf(BusinessLogicException.class);
    }

    @Test
    void get() {
        assertThat(userService.get(TEST_DEVELOPER_ID))
                .usingRecursiveComparison()
                .isEqualTo(TEST_DEVELOPER);
    }

}