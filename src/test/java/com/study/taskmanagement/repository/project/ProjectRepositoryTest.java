package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import com.study.taskmanagement.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProjectRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void getById() {
        final int projectId = 100_000;
        Project project = projectRepository.findById(projectId).get();
        assertEquals(projectId, project.getId());
        assertEquals("Tasks Management", project.getName());
        assertEquals("Jane Doe", project.getManager().getName());
    }

    @Test
    @DirtiesContext
    void create() {
        Project newProject = new Project();
        newProject.setName("New");
        User manager = userRepository.findById(100001).get();
        newProject.setManager(manager);
        Project persisted = projectRepository.save(newProject);
        assertEquals(manager.getName(), persisted.getManager().getName());
    }

    @Test
    @DirtiesContext
    void delete() {
        final int projectId = 100_000;
        projectRepository.deleteById(projectId);
        Optional<Project> project = projectRepository.findById(projectId);
        assertFalse(project.isPresent());
    }

    @Test
    @DirtiesContext
    void update() {
        final int projectId = 100_000;
        Project projectToUpdate = projectRepository.findById(projectId).get();
        projectToUpdate.setName("Updated Name");
        Project updated = projectRepository.save(projectToUpdate);
        assertEquals("Updated Name", updated.getName());
    }

}