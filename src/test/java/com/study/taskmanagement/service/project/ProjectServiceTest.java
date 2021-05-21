package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.project.ProjectTestData;
import com.study.taskmanagement.service.BaseServiceTest;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceTest
        extends BaseServiceTest {

    @MockBean
    ProjectRepository mockProjectRepository;

    @Autowired
    ProjectService projectService;

    @BeforeEach
    void setUp() {
        reset(mockProjectRepository);
        when(mockProjectRepository.save(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mockProjectRepository.findById(ProjectTestData.TEST_PROJECT_ID))
                .thenReturn(Optional.of(ProjectTestData.TEST_PROJECT));
    }

    @Test
    void update() {
        final String updatedName = "Updated";
        final Project projectToUpdate = ProjectTestData.copyOf(ProjectTestData.TEST_PROJECT);
        projectToUpdate.setName(updatedName);
        assertThat(projectService.update(projectToUpdate))
                .hasFieldOrPropertyWithValue("name", updatedName);
    }

    @Test
    void updateAbsent() {
        final Project absentProject = new Project(null, null, null);
        assertThatThrownBy(() -> {
            projectService.update(absentProject);
        }).isInstanceOf(BusinessLayerException.class);
        verify(mockProjectRepository, times(0)).save(any(Project.class));
    }

    @Test
    void get() {
        assertThat(projectService.get(ProjectTestData.TEST_PROJECT_ID))
                .usingRecursiveComparison()
                .ignoringFields("manager", "tasks")
                .isEqualTo(ProjectTestData.TEST_PROJECT);
    }

    @Test
    void getAbsent() {
        final int absentId = 100_500;
        assertThatThrownBy(() -> {
            projectService.get(absentId);
        }).isInstanceOf(BusinessLayerException.class);
    }

    @Test
    void delete() {
        projectService.delete(ProjectTestData.TEST_PROJECT);
        verify(mockProjectRepository, times(1)).delete(any(Project.class));
    }

}
