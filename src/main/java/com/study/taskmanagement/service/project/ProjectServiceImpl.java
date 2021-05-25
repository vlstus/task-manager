package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl
        extends AbstractService<Project, Integer>
        implements ProjectService {

    protected ProjectServiceImpl(ProjectRepository projectRepository) {
        super(projectRepository);
    }


    @Override
    @Transactional
    public Project create(Project project) {
        setCurrentManager(project);
        return super.create(project);
    }



    @Override
    @Transactional
    public Project update(Project updated, Integer id) {
        final Project existing = crudRepository.findById(id)
                .orElseThrow(BusinessLayerException::new);
        updated.setTasks(existing.getTasks());
        return super.update(updated, id);
    }

    // TODO: 25-May-21 GET CURRENT LOGGED IN MANAGER FROM SECURITY CONTEXT
    private void setCurrentManager(Project project) {
        project.setManager(new User("Jane Doe", "Password", Role.MANAGER));
        project.getManager().setId(100001);
    }

}
