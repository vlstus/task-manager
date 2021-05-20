package com.study.taskmanagement.service.admin;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.repository.user.UserTestData;
import com.study.taskmanagement.service.BaseServiceTest;
import com.study.taskmanagement.service.exception.BusinessLogicException;
import com.study.taskmanagement.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MockBeans(
        {
                @MockBean(value = UserRepository.class)
        }
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminServiceTest
        extends BaseServiceTest {

    @TestConfiguration
    static class AdminServiceTestConfiguration {

        @Autowired
        UserRepository userRepository;

        @Bean
        public AdminService adminService() {
            initMock();
            return new SimpleAdminService(userRepository);
        }

        private void initMock() {
            final User testUser = UserTestData.copyOf(UserTestData.TEST_DEVELOPER);
            testUser.setId(UserTestData.TEST_DEVELOPER_ID);
            Mockito.when(userRepository.findById(UserTestData.TEST_DEVELOPER_ID))
                    .thenReturn(Optional.of(testUser));

            Mockito.when(userRepository.save(Mockito.argThat(argument ->
                    Objects.nonNull(argument) && EntityUtils.isNew(argument))))
                    .thenAnswer((Answer<User>) invocation -> {
                        User user = invocation.getArgument(0);
                        user.setId((int) (Math.random() * 10));
                        return user;
                    });

            Mockito.when(userRepository.save(Mockito.argThat(argument ->
                    Objects.nonNull(argument) && EntityUtils.exists(argument))))
                    .thenAnswer((Answer<User>) invocation -> UserTestData.copyOf(invocation.getArgument(0)));

            Mockito.doThrow(IllegalArgumentException.class)
                    .when(userRepository)
                    .delete(Mockito.argThat(argument ->
                            Objects.nonNull(argument) &&
                                    Objects.nonNull(argument.getId()) &&
                                    !argument.getId().equals(UserTestData.TEST_DEVELOPER_ID)));
        }

    }

    @Autowired
    AdminService adminService;

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void create() {
        final User userToCreate = UserTestData.copyOf(UserTestData.getNew());
        assertThat(adminService.create(userToCreate))
                .extracting(BaseEntity::getId)
                .satisfies(identifier ->
                        assertThat(identifier).isPositive());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createExisting() {
        final User existing = UserTestData.copyOf(UserTestData.TEST_DEVELOPER);
        existing.setId(UserTestData.TEST_DEVELOPER_ID);
        assertThatThrownBy(() ->
                adminService.create(existing))
                .isInstanceOf(BusinessLogicException.class);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void update() {
        final String updatedName = "New Name";
        final User existing = adminService.get(UserTestData.TEST_DEVELOPER_ID);
        existing.setName(updatedName);
        assertThat(adminService.update(existing))
                .extracting(User::getName)
                .isEqualTo(updatedName);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateAbsent() {
        assertThatThrownBy(() ->
                adminService.update(UserTestData.getNew()))
                .isInstanceOf(BusinessLogicException.class);
    }

    @Test
    void get() {
        assertThat(adminService.get(UserTestData.TEST_DEVELOPER_ID))
                .usingRecursiveComparison()
                .ignoringFields("id", "role.id")
                .isEqualTo(UserTestData.TEST_DEVELOPER);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void delete() {
        final User userToDelete = UserTestData.copyOf(UserTestData.TEST_DEVELOPER);
        userToDelete.setId(UserTestData.TEST_DEVELOPER_ID);
        assertThatThrownBy(() -> {
            adminService.delete(userToDelete);
            final int absentId = 100_500;
            userToDelete.setId(absentId);
            adminService.delete(userToDelete);
        })
                .isInstanceOf(BusinessLogicException.class);
    }

}