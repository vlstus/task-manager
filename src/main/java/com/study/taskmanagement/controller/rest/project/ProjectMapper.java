package com.study.taskmanagement.controller.rest.project;

import com.study.taskmanagement.dto.project.ProjectDTO;
import com.study.taskmanagement.model.project.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") interface ProjectMapper {
    ProjectDTO convertProjectToProjectDTO(Project model);
    Project convertProjectDTOToProjectDTO(ProjectDTO transferObject);
}
