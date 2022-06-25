package com.study.taskmanagement.controller.rest.project;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.controller.rest.MappingService;
import com.study.taskmanagement.dto.project.ProjectDTO;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.service.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/projects")
@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
public class ProjectRestController
        extends AbstractRestController<Project, ProjectDTO, Integer> {

    public ProjectRestController(final ProjectService projectService,
                                    final MappingService<Project, ProjectDTO> mappingService) {
        super(projectService, mappingService);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Collection<ProjectDTO>> getAll() {
        return super.getAll();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ProjectDTO> getById(@PathVariable final Integer id) {
        return super.getById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ProjectDTO> create(@Valid @RequestBody final ProjectDTO project) {
        return super.create(project);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@Valid @RequestBody final ProjectDTO project, @PathVariable final Integer id) {
        super.update(project, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable final Integer id) {
        super.delete(id);
    }

}
