package com.study.taskmanagement.controller.rest.task;

import com.study.taskmanagement.dto.task.TaskDTO;
import com.study.taskmanagement.model.project.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") interface TaskMapper {
    TaskDTO convertTaskTOTaskDTO(Task task);
    Task convertTaskDTOToTask(TaskDTO taskDTO);
}
