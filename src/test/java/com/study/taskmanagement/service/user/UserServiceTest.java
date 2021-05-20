package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.repository.user.UserTestData;
import com.study.taskmanagement.service.BaseServiceTest;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@MockBean(UserRepository.class)
class UserServiceTest extends
        BaseServiceTest {

    @Autowired
    UserRepository mockUserRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        reset(mockUserRepository);
        when(mockUserRepository.findById(UserTestData.TEST_DEVELOPER_ID))
                .thenReturn(Optional.of(UserTestData.TEST_DEVELOPER));
        when(mockUserRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void create() {
        final String createdUserName = "Created User";
        assertThat(userService.create(new User(createdUserName, "Pass", Role.ADMIN)))
                .hasFieldOrPropertyWithValue("name", createdUserName);
    }

    @Test
    void update() {
        final String updatedName = "Updated Name";
        final User userToUpdate = UserTestData.copyOf(UserTestData.TEST_DEVELOPER);
        userToUpdate.setName(updatedName);
        assertThat(userService.update(userToUpdate))
                .hasFieldOrPropertyWithValue("name", updatedName);
    }

    @Test
    void updateAbsent() {
        assertThatThrownBy(() ->
                userService.update(new User("New User", "Pass", Role.ADMIN)))
                .isInstanceOf(BusinessLayerException.class);
    }

    @Test
    void get() {
        assertThat(userService.get(UserTestData.TEST_DEVELOPER_ID))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(UserTestData.TEST_DEVELOPER);
    }

    @Test
    void delete() {
        userService.delete(UserTestData.TEST_DEVELOPER);
        verify(mockUserRepository, times(1))
                .delete(UserTestData.TEST_DEVELOPER);
    }

}