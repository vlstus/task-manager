package com.study.taskmanagement.service.task;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.project.TaskRepository;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        fetchTaskData(task);
        return super.create(task);
    }

    @Override
    @Transactional
    public Task update(Task task, Integer id) {
        fetchTaskData(task);
        return super.update(task, id);
    }

    @Override
    public Task get(Integer id) {
        log.info("Getting entity with id {}", id);
        return ((TaskRepository) crudRepository).findByIdWithStaffIfExists(id)
                .orElseThrow(()-> new NotFoundException("application.tasks.notFound"));
    }

    @Override
    public Collection<Task> getAllByUser(Integer userId, Role role) {
        log.info("Getting all task for user with id {}", userId);
        TaskRepository taskRepository = (TaskRepository) crudRepository;
        Iterable<Task> userTasks = Collections.emptyList();
        if (role.equals(Role.ROLE_DEVELOPER)) {
            userTasks = taskRepository.findAllByDeveloperId(userId);
        } else if (role.equals(Role.ROLE_MANAGER)) {
            userTasks = taskRepository.findAllByManagerId(userId);
        }
        return StreamSupport.stream(userTasks.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        crudRepository.deleteById(id);
    }

    private void fetchTaskData(Task task) {
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
                .orElseThrow(() -> new NotFoundException("application.tasks.staff.notFound"));
    }

    private Project fetchProject(Project project) {
        log.info("Fetching manager data form {}", project);
        return projectRepository.findByName(project.getName())
                .orElseThrow(() -> new NotFoundException("application.tasks.project.notFound"));
    }

}
