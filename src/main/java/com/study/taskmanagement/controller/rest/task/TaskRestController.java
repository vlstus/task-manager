package com.study.taskmanagement.controller.rest.task;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskRestController
        extends AbstractRestController<Task, Integer> {

    protected TaskRestController(CrudService<Task, Integer> crudService) {
        super(crudService);
    }

}
