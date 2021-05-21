package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl
        extends AbstractService<Project, Integer>
        implements ProjectService {

    protected ProjectServiceImpl(ProjectRepository projectRepository) {
        super(projectRepository);
    }

}
