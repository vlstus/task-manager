package com.study.taskmanagement.service.task;

import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.service.CrudService;

import java.util.Collection;

public interface TaskService
        extends CrudService<Task, Integer> {

    Collection<Task> getAllByUser(Integer userId, Role role);

    void deleteById(Integer id);
}
