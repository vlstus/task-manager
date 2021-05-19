package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository
        extends CrudRepository<Task, Integer> {

}
