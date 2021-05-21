package com.study.taskmanagement.service.task;

import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.repository.project.TaskRepository;
import com.study.taskmanagement.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl
        extends AbstractService<Task, Integer>
        implements TaskService {

    protected TaskServiceImpl(TaskRepository taskRepository) {
        super(taskRepository);
    }

}
