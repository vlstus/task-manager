package com.study.taskmanagement.service.developer;

import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.repository.project.TaskRepository;
import com.study.taskmanagement.service.exception.BusinessLogicException;
import com.study.taskmanagement.service.logging.annotation.LogMethodNameAndParameters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SimpleDeveloperService
        implements DeveloperService {

    private final TaskRepository taskRepository;

    @Override
    @LogMethodNameAndParameters
    public Status getStatusOf(Integer taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(BusinessLogicException::new)
                .getStatus();
    }

    @Override
    @Transactional
    @LogMethodNameAndParameters
    public void setStatusOf(Integer taskId, Status status) {
        final Task task = taskRepository.findById(taskId)
                .orElseThrow(BusinessLogicException::new);
        task.setStatus(status);
        taskRepository.save(task);
    }

}
