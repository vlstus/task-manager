package com.study.taskmanagement.service.task;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.project.TaskRepository;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl
        extends AbstractService<Task, Integer>
        implements TaskService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    protected TaskServiceImpl(TaskRepository taskRepository,
                              UserRepository userRepository, ProjectRepository projectRepository) {
        super(taskRepository);
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public Task create(Task task) {
        fetchTask(task);
        return super.create(task);
    }

    @Override
    @Transactional
    public Task update(Task task, Integer id) {
        fetchTask(task);
        return super.update(task, id);
    }

    private void fetchTask(Task task) {
        final User manager = fetchUser(task.getManager());
        final User developer = fetchUser(task.getDeveloper());
        final Project project = fetchProject(task.getProject());
        task.setManager(manager);
        task.setDeveloper(developer);
        task.setProject(project);
    }

    private User fetchUser(User user) {
        log.info("Fetching user data form {}", user);
        return userRepository.findByName(user.getName())
                .orElseThrow(() -> new BusinessLayerException("USER DOES NOT EXIST"));
    }

    private Project fetchProject(Project project) {
        log.info("Fetching manager data form {}", project);
        return projectRepository.findByName(project.getName())
                .orElseThrow(() -> new BusinessLayerException("PROJECT DOES NOT EXIST"));
    }

}
