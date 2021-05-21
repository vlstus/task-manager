package com.study.taskmanagement.controller.task;

import com.study.taskmanagement.controller.AbstractRestController;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks/")
public class TaskRestControllerImpl
        extends AbstractRestController<Task, Integer>
        implements TaskRestController {

    protected TaskRestControllerImpl(CrudService<Task, Integer> crudService) {
        super(crudService);
    }

}
