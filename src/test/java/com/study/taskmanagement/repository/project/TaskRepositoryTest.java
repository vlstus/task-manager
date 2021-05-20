package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import com.study.taskmanagement.repository.exception.RepositoryLayerException;
import com.study.taskmanagement.repository.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.study.taskmanagement.repository.project.ProjectTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class TaskRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    StatusRepository statusRepository;

    @Test
    void findAll() {
        assertThat(taskRepository.findAll())
                .isNotEmpty();
    }

    @Test
    void findById() {
        assertThat(taskRepository.findById(TaskTestData.TEST_TASK_ID))
                .isPresent()
                .hasValueSatisfying(task ->
                        assertThat(task)
                                .usingRecursiveComparison()
                                .ignoringFields("id", "status.id", "manager", "developers")
                                .isEqualTo(TaskTestData.TEST_TASK));
    }

    @Test
    void create() {
        final String newTaskName = "Implement business logic";
        Task newTask = new Task(newTaskName,
                UserTestData.TEST_MANAGER,
                StatusTestData.TEST_STATUS,
                Collections.singletonList(UserTestData.copyOf(UserTestData.TEST_DEVELOPER)));
        assertThat(taskRepository.save(newTask))
                .hasFieldOrPropertyWithValue("name", newTaskName);
    }

    @Test
    void update() {
        final String updatedTaskName = "Updated Task";
        final Task updated = TaskTestData.copyOf(TaskTestData.TEST_TASK);
        updated.setName(updatedTaskName);
        assertThat(taskRepository.save(updated))
                .hasFieldOrPropertyWithValue("name", updatedTaskName);
    }

    @Test
    void delete() {
        taskRepository.deleteById(TaskTestData.TEST_TASK_ID);
        assertThat(taskRepository.findById(TaskTestData.TEST_TASK_ID))
                .isEmpty();
    }

}