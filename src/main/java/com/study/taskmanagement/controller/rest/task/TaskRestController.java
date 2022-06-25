package com.study.taskmanagement.controller.rest.task;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.controller.rest.MappingService;
import com.study.taskmanagement.dto.task.TaskDTO;
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
        extends AbstractRestController<Task, TaskDTO, Integer> {

    public TaskRestController(final CrudService<Task, Integer> crudService,
                              final MappingService<Task, TaskDTO> mappingService) {
        super(crudService, mappingService);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Collection<TaskDTO>> getAll() {
        return super.getAll();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @Override
    public ResponseEntity<TaskDTO> getById(@PathVariable final Integer id) {
        return super.getById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody final TaskDTO transferObject) {
        return super.create(transferObject);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER') or hasRole('DEVELOPER') or hasRole('ADMIN')")
    @Override
    public void update(@Valid @RequestBody TaskDTO transferObject, @PathVariable final Integer id) {
        super.update(transferObject, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @Override
    public void delete(@PathVariable Integer id) {
        ((TaskService) crudService).deleteById(id);
    }

}
