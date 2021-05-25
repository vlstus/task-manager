package com.study.taskmanagement.controller.view.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskViewResolverController {

    @GetMapping(path = "/tasks")
    public String tasks() {
        return "tasks";
    }

}
