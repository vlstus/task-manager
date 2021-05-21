package com.study.taskmanagement.controller.project;

import com.study.taskmanagement.controller.AbstractRestController;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.service.project.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects/")
public class ProjectRestControllerImpl
        extends AbstractRestController<Project, Integer>
        implements ProjectRestController {

    protected ProjectRestControllerImpl(ProjectService projectService) {
        super(projectService);
    }

}
