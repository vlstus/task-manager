package com.study.taskmanagement.service.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.service.CrudService;

import java.util.Collection;

public interface ProjectService
        extends CrudService<Project, Integer> {

    Collection<Project> getAllByUserId(Integer userId);

}
