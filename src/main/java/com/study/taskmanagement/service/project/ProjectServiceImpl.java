package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public Collection<Project> getAllByUserId(Integer userId) {
        log.info("Getting all projects for user with id {}",
                userId);
        ProjectRepository projectRepository = ((ProjectRepository) crudRepository);
        return StreamSupport.stream(projectRepository.findByManagerId(userId).spliterator(), false)
                .collect(Collectors.toList());
    }

    private User fetchManager(User manager) {
        log.info("Fetching manager data form {}", manager);
        return userRepository.findByName(manager.getName())
                .orElseThrow(() -> new BusinessLayerException("MANAGER NOT EXIST"));
    }

}
