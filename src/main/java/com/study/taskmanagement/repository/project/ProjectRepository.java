package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository
        extends CrudRepository<Project, Integer> {

    Optional<Project> findByName(String name);

}
