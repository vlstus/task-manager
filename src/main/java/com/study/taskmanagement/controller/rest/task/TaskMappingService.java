package com.study.taskmanagement.controller.rest.task;

import com.study.taskmanagement.controller.rest.MappingService;
import com.study.taskmanagement.dto.task.TaskDTO;
import com.study.taskmanagement.model.project.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component class TaskMappingService
        implements MappingService<Task, TaskDTO> {
    private final TaskMapper taskMapper;
    @Override
    public Task convertToModel(TaskDTO taskDTO) {
        return taskMapper.convertTaskDTOToTask(taskDTO);
    }

    @Override
    public TaskDTO convertToTransferObject(Task task) {
        return taskMapper.convertTaskTOTaskDTO(task);
    }
}
