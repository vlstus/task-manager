package com.study.taskmanagement.controller.view.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TaskViewResolverController {

    @GetMapping(path = "/tasks")
    public String tasks() {
        return "tasks";
    }

}
