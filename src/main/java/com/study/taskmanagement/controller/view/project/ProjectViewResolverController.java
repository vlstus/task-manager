package com.study.taskmanagement.controller.view.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ProjectViewResolverController {

    @GetMapping(path = "/projects")
    public String projects() {
        return "projects";
    }

}
