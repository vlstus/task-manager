package com.study.taskmanagement.service.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.repository.user.UserTestData;
import com.study.taskmanagement.service.BaseServiceTest;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest
        extends BaseServiceTest {

    @MockBean
    UserRepository mockUserRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        reset(mockUserRepository);
        when(mockUserRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mockUserRepository.findById(UserTestData.TEST_DEVELOPER_ID))
                .thenReturn(Optional.of(UserTestData.TEST_DEVELOPER));
        when(mockUserRepository.existsById(UserTestData.TEST_MANAGER_ID))
                .thenReturn(true);
    }

    @ParameterizedTest
    @EnumSource(Role.class)
    void create(Role role) {
        assertThat(userService.create(new User("New User", "Password", role)))
                .hasFieldOrPropertyWithValue("role", role);
    }

    @Test
    void update() {
        final String updatedName = "Updated";
        final User managerToUpdate = UserTestData.copyOf(UserTestData.TEST_MANAGER);
        managerToUpdate.setName(updatedName);
        assertThat(userService.update(managerToUpdate, managerToUpdate.getId()))
                .hasFieldOrPropertyWithValue("name", updatedName);
    }

    @Test
    void updateAbsent() {
        final User absentUser = new User("Absent", "NoPass", Role.ADMIN);
        assertThatThrownBy(() -> {
            userService.update(absentUser, UserTestData.TEST_DEVELOPER_ID);
        }).isInstanceOf(BusinessLayerException.class);
        verify(mockUserRepository, times(0)).save(any(User.class));
    }

    @Test
    void get() {
        assertThat(userService.get(UserTestData.TEST_DEVELOPER_ID))
                .usingRecursiveComparison()
                .isEqualTo(UserTestData.TEST_DEVELOPER);
    }

    @Test
    void getAbsent() {
        final int absentId = 100_500;
        assertThatThrownBy(() -> {
            userService.get(absentId);
        }).isInstanceOf(BusinessLayerException.class);
    }

    @Test
    void delete() {
        userService.delete(UserTestData.TEST_MANAGER);
        verify(mockUserRepository, times(1)).delete(any(User.class));
    }

}
