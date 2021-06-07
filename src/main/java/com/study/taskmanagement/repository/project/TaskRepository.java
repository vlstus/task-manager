package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository
        extends CrudRepository<Task, Integer> {

    @Query(
            value = "SELECT " +
                    "task.id ," +
                    "task.name ," +
                    "task.manager_id ," +
                    "task.developer_id ," +
                    "task.project_id ," +
                    "task.status ," +
                    "FROM tasks task " +
                    "LEFT JOIN " +
                    "users user " +
                    "ON task.manager_id = user.id OR task.developer_id = user.id " +
                    "LEFT JOIN " +
                    "projects project " +
                    "ON task.project_id = project.id " +
                    "WHERE " +
                    "task.id = ?1",
            nativeQuery = true
    )
    Optional<Task> findByIdWithStaffIfExists(Integer id);

    Iterable<Task> findAllByDeveloperId(Integer developerId);

    Iterable<Task> findAllByManagerId(Integer managerId);

}
