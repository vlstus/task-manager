package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class SimpleProjectService
        extends AbstractService<Project, Integer>
        implements ProjectService {

    protected SimpleProjectService(ProjectRepository projectRepository) {
        super(projectRepository);
    }

}
