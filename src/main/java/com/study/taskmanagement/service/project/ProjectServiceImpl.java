package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
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
    public Project update(Project updated, Integer id) {
        log.info("Updating entity {}", updated);
        if (updated.getId() == null ||
                !(updated.getId().equals(id))) {
            log.warn("Failed to update entity {}", updated);
            throw new BusinessLayerException();
        }
        final Project existing = crudRepository.findById(id)
                .orElseThrow(BusinessLayerException::new);
        updated.setTasks(existing.getTasks());
        return crudRepository.save(updated);
    }

}
