package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository
        extends CrudRepository<Status, Integer> {

    Optional<Status> findByStatusType(Status.Type type);

}
