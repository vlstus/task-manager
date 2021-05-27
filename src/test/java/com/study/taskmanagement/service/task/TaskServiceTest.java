package com.study.taskmanagement.service.task;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.project.ProjectTestData;
import com.study.taskmanagement.repository.project.TaskRepository;
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

public class TaskServiceTest
        extends BaseServiceTest {

    @MockBean
    TaskRepository mockTaskRepository;
    @MockBean
    UserRepository mockUserRepository;
    @MockBean
    ProjectRepository mockProjectRepository;

    @Autowired
    TaskService taskService;

    @BeforeEach
    void setUp() {
        reset(mockTaskRepository);
        when(mockTaskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mockTaskRepository.findById(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .thenReturn(Optional.of(ProjectTestData.TaskTestData.TEST_TASK));
        when(mockTaskRepository.existsById(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .thenReturn(true);
        when(mockTaskRepository.findByIdWithStaffIfExists(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .thenReturn(Optional.of(ProjectTestData.TaskTestData.TEST_TASK));

        reset(mockUserRepository);
        when(mockUserRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mockUserRepository.findById(UserTestData.TEST_DEVELOPER_ID))
                .thenReturn(Optional.of(UserTestData.TEST_DEVELOPER));
        when(mockUserRepository.existsById(UserTestData.TEST_MANAGER_ID))
                .thenReturn(true);
        when(mockUserRepository.findByName(UserTestData.TEST_DEVELOPER.getName()))
                .thenReturn(Optional.of(UserTestData.TEST_DEVELOPER));
        when(mockUserRepository.findByName(UserTestData.TEST_MANAGER.getName()))
                .thenReturn(Optional.of(UserTestData.TEST_MANAGER));

        reset(mockProjectRepository);
        when(mockProjectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mockProjectRepository.findById(ProjectTestData.TEST_PROJECT_ID))
                .thenReturn(Optional.of(ProjectTestData.TEST_PROJECT));
        when(mockProjectRepository.existsById(ProjectTestData.TEST_PROJECT_ID))
                .thenReturn(true);
        when(mockProjectRepository.findByName(ProjectTestData.TEST_PROJECT.getName()))
                .thenReturn(Optional.of(ProjectTestData.TEST_PROJECT));
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    void create(Status status) {
        assertThat(taskService.create(new Task("New Task",
                UserTestData.TEST_MANAGER,
                status,
                UserTestData.TEST_DEVELOPER,
                ProjectTestData.TEST_PROJECT)))
                .hasFieldOrPropertyWithValue("status", status);
    }

    @Test
    void update() {
        final String updatedName = "Updated";
        final Task taskToUpdate = ProjectTestData.TaskTestData.copyOf(ProjectTestData.TaskTestData.TEST_TASK);
        taskToUpdate.setName(updatedName);
        assertThat(taskService.update(taskToUpdate, taskToUpdate.getId()))
                .hasFieldOrPropertyWithValue("name", updatedName);
    }

    @Test
    void updateAbsent() {
        assertThatThrownBy(() -> {
            taskService.update(ProjectTestData.TaskTestData.ABSENT_TASK, ProjectTestData.TaskTestData.TEST_TASK_ID);
        }).isInstanceOf(BusinessLayerException.class);
        verify(mockTaskRepository, times(0)).save(any(Task.class));
    }

    @Test
    void get() {
        assertThat(taskService.get(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .usingRecursiveComparison()
                .ignoringFields("manager", "developer", "project")
                .isEqualTo(ProjectTestData.TaskTestData.TEST_TASK);
    }

    @Test
    void getAbsent() {
        final int absentId = 100_500;
        assertThatThrownBy(() -> {
            taskService.get(absentId);
        }).isInstanceOf(BusinessLayerException.class);
    }

    @Test
    void delete() {
        taskService.delete(ProjectTestData.TaskTestData.TEST_TASK);
        verify(mockTaskRepository, times(1)).delete(any(Task.class));
    }

}
