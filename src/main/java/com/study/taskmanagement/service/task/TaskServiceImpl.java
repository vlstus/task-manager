package com.study.taskmanagement.service.task;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.project.ProjectRepository;
import com.study.taskmanagement.repository.project.TaskRepository;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.AbstractService;
import com.study.taskmanagement.service.project.ProjectService;
import com.study.taskmanagement.service.user.UserService;
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
        fetchData(task);
        return super.create(task);
    }

    @Override
    @Transactional
    public Task update(Task task, Integer entityId) {
        fetchData(task);
        return super.update(task, entityId);
    }

    private void fetchData(Task task) {
        final User taskManager = userRepository.findByName(task.getManager().getName());
        final User taskDeveloper = userRepository.findByName(task.getDeveloper().getName());
        final Project taskProject = projectRepository.findByName(task.getProject().getName());
        task.setManager(taskManager);
        task.setDeveloper(taskDeveloper);
        task.setProject(taskProject);
    }

}
