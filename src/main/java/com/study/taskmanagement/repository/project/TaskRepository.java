package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository
        extends CrudRepository<Task, Integer> {

    Iterable<Task> findAllByDeveloperId(Integer developerId);

    Iterable<Task> findAllByManagerId(Integer managerId);

    @Query(
            value = """
                    SELECT
                        tsk.id ,
                        tsk.name ,
                        tsk.manager_id ,
                        tsk.project_id ,
                        tsk.developer_id ,
                        tsk.status
                    FROM tasks tsk
                    LEFT JOIN users usr
                        ON tsk.manager_id = usr.id OR tsk.developer_id = usr.id
                    LEFT JOIN projects prj
                        ON tsk.project_id = prj.id
                    WHERE tsk.id = ?1
                    """,
            nativeQuery = true
    )
    Optional<Task> findByIdWithStaffIfExists(Integer id);

}
