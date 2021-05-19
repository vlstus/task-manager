package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import com.study.taskmanagement.repository.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.study.taskmanagement.repository.user.UserTestData.TEST_MANAGER;
import static com.study.taskmanagement.repository.user.UserTestData.TEST_MANAGER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void findAll() {
        assertThat(projectRepository.findAll())
                .isNotEmpty();
    }

    @Test
    void findById() {
        assertThat(projectRepository.findById(ProjectTestData.TEST_PROJECT_ID))
                .isPresent()
                .hasValueSatisfying(project ->
                        assertThat(project)
                                .usingRecursiveComparison()
                                .ignoringFields("id", "manager.id", "manager.role.id", "tasks")
                                .isEqualTo(ProjectTestData.TEST_PROJECT));
    }

    @Test
    void create() {
        User manager = UserTestData.copyOf(TEST_MANAGER);
        manager.setId(TEST_MANAGER_ID);
        Project newProject = new Project("CRUD app", manager, Collections.emptyList());
        assertThat(projectRepository.save(newProject))
                .extracting(Project::getName)
                .isEqualTo("CRUD app");
    }

    @Test
    void update() {
        Project updated = ProjectTestData.getUpdated();
        updated.getManager().setId(TEST_MANAGER_ID);
        assertThat(projectRepository.save(updated))
                .extracting(Project::getName)
                .isEqualTo(ProjectTestData.UPDATED_NAME);
    }

    @Test
    void delete() {
        projectRepository.deleteById(ProjectTestData.TEST_PROJECT_ID);
        assertThat(projectRepository.findById(ProjectTestData.TEST_PROJECT_ID))
                .isEmpty();
    }

}