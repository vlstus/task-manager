package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.study.taskmanagement.repository.user.UserTestData.TEST_MANAGER;
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
        final Project newProject = new Project("CRUD app", TEST_MANAGER, Collections.emptyList());
        assertThat(projectRepository.save(newProject))
                .extracting(Project::getName)
                .isEqualTo("CRUD app");
    }

    @Test
    void update() {
        final String updateName = "Updated Project";
        Project updated = ProjectTestData.copyOf(ProjectTestData.TEST_PROJECT);
        updated.setName(updateName);
        assertThat(projectRepository.save(updated))
                .hasFieldOrPropertyWithValue("name", updateName);
    }

    @Test
    void delete() {
        projectRepository.deleteById(ProjectTestData.TEST_PROJECT_ID);
        assertThat(projectRepository.findById(ProjectTestData.TEST_PROJECT_ID))
                .isEmpty();
    }

}