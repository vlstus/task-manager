package com.study.taskmanagement.service.developer;

import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.service.exception.BusinessLogicException;

public interface DeveloperService {

    /**
     * Gets the current status of Task by its' ID
     *
     * @param taskId ID of the task
     * @return current status of task with ID
     * @throws BusinessLogicException if no Task with id found
     */
    Status getStatusOf(Integer taskId);

    /**
     * Sets status of task with ID to status passed as parameter
     *
     * @param taskId ID of the task
     * @param status Status to be set
     */
    void setStatusOf(Integer taskId, Status status);

}
