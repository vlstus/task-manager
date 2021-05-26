package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl
        extends AbstractService<Project, Integer>
        implements ProjectService {

    private final UserRepository userRepository;

    protected ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        super(projectRepository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Project create(Project project) {
        project.setManager(fetchManager(project.getManager()));
        return super.create(project);
    }

    @Override
    @Transactional
    public Project update(Project project, Integer entityId) {
        project.setManager(fetchManager(project.getManager()));
        return super.update(project, entityId);
    }

    private User fetchManager(User manager) {
        return userRepository.findByName(manager.getName())
                .orElseThrow(() -> new BusinessLayerException("MANAGER NOT EXIST"));
    }

}
