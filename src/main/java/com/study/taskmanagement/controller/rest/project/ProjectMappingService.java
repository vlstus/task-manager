package com.study.taskmanagement.controller.rest.project;

import com.study.taskmanagement.controller.rest.MappingService;
import com.study.taskmanagement.dto.project.ProjectDTO;
import com.study.taskmanagement.model.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component class ProjectMappingService
        implements MappingService<Project, ProjectDTO> {
    private final ProjectMapper projectMapper;
    @Override
    public Project convertToModel(final ProjectDTO projectDTO) {
        return projectMapper.convertProjectDTOToProjectDTO(projectDTO);
    }
    @Override
    public ProjectDTO convertToTransferObject(final Project project) {
        return projectMapper.convertProjectToProjectDTO(project);
    }
}
