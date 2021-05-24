package com.study.taskmanagement.controller.rest.project;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.service.project.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects/")
public class ProjectRestController
        extends AbstractRestController<Project, Integer> {

    protected ProjectRestController(ProjectService projectService) {
        super(projectService);
    }

}
