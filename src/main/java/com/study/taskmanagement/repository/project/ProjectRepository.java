package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository
        extends CrudRepository<Project, Integer> {

    Optional<Project> findByName(String name);

    @Query(
            value = "SELECT " +
                    "project.id ," +
                    "project.manager_id ," +
                    "project.name ," +
                    "user.name ," +
                    "user.password ," +
                    "user.role " +
                    "FROM " +
                    "projects project " +
                    "LEFT JOIN " +
                    "users user " +
                    "ON project.manager_id=user.id " +
                    "WHERE " +
                    "project.id = ?1",
            nativeQuery = true
    )
    Optional<Project> findByIdWithManagerIfExists(Integer id);

    Iterable<Project> findByManagerId(Integer managerId);

}
