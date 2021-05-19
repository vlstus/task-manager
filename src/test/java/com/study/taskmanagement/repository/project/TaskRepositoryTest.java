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
        assertThat(taskRepository.findById(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .isPresent()
                .hasValueSatisfying(task ->
                        assertThat(task)
                                .usingRecursiveComparison()
                                .ignoringFields("id", "status.id", "manager", "developers")
                                .isEqualTo(ProjectTestData.TaskTestData.TEST_TASK));
    }

    @Test
    void create() {
        User testManager = UserTestData.copyOf(UserTestData.TEST_MANAGER);
        testManager.setId(UserTestData.TEST_MANAGER_ID);
        Status newStatus = Status.ofType("TO_DO");
        newStatus.setId(getStatusId(newStatus));
        Task newTask = new Task("Implement business logic",
                testManager,
                newStatus,
                Collections.singletonList(UserTestData.copyOf(UserTestData.TEST_DEVELOPER)));
        taskRepository.save(newTask);
    }

    @Test
    void update() {
        Task updated = ProjectTestData.TaskTestData.getUpdated();
        updated.getStatus().setId(getStatusId(updated.getStatus()));
        assertThat(taskRepository.save(updated))
                .extracting(Task::getName)
                .isEqualTo(ProjectTestData.TaskTestData.UPDATED_NAME);
    }

    @Test
    void delete() {
        taskRepository.deleteById(ProjectTestData.TaskTestData.TEST_TASK_ID);
        assertThat(taskRepository.findById(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .isEmpty();
    }

    private int getStatusId(Status status) {
        return statusRepository.findByStatusType(status.getStatusType())
                .orElseThrow(RepositoryLayerException::new).getId();
    }

}