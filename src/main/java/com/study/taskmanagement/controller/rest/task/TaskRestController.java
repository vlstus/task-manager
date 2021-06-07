package com.study.taskmanagement.controller.rest.task;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.service.CrudService;
import com.study.taskmanagement.service.task.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/tasks")
@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
public class TaskRestController
        extends AbstractRestController<Task, Integer> {

    protected TaskRestController(CrudService<Task, Integer> crudService) {
        super(crudService);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Collection<Task>> getAll() {
        return super.getAll();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @Override
    public ResponseEntity<Task> getById(@PathVariable Integer id) {
        return super.getById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Task> create(@Valid @RequestBody Task entity) {
        return super.create(entity);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @Override
    public void update(@Valid @RequestBody Task entity, @PathVariable Integer id) {
        super.update(entity, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @Override
    public void delete(@PathVariable Integer id) {
        ((TaskService) crudService).deleteById(id);
    }

}
