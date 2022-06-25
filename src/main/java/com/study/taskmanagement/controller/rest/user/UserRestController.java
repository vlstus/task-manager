package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.controller.rest.MappingService;
import com.study.taskmanagement.dto.user.UserDTO;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.security.UserDetailsImpl;
import com.study.taskmanagement.service.exception.NotOwnedException;
import com.study.taskmanagement.service.project.ProjectService;
import com.study.taskmanagement.service.task.TaskService;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
class UserRestController
        extends AbstractRestController<User, UserDTO, Integer> {

    private final ProjectService projectService;
    private final TaskService taskService;

    public UserRestController(final UserService userService,
                                 final ProjectService projectService,
                                 final TaskService taskService,
                                 final MappingService<User, UserDTO> mappingService) {
        super(userService, mappingService);
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping(params = {"role"})
    public List<UserDTO> getByRole(@RequestParam("role") final Role role) {
        final UserService userService = (UserService) crudService;
        return userService.getByRole(role)
                .stream()
                .map(mappingService::convertToTransferObject)
                .collect(toList());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Collection<UserDTO>> getAll() {
        return super.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UserDTO> getById(@PathVariable final Integer id) {
        return super.getById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UserDTO> create(@Valid @RequestBody final UserDTO transferObject) {
        return super.create(transferObject);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@Valid @RequestBody final UserDTO entity, @PathVariable final Integer id) {
        super.update(entity, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Override
    public void delete(@PathVariable final Integer id) {
        super.delete(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(path = "/{userId}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable final Integer userId,
                                                     @AuthenticationPrincipal final UserDetailsImpl loggedUser) {
        checkIfOwn(userId, loggedUser);
        return ResponseEntity.ok(new ArrayList<>(projectService.getAllByUserId(userId)));
    }

    @PreAuthorize("hasAnyRole('DEVELOPER','MANAGER')")
    @GetMapping(path = "/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable final Integer userId,
                                               @AuthenticationPrincipal final UserDetailsImpl loggedUser) {
        checkIfOwn(userId, loggedUser);
        return ResponseEntity.ok(new ArrayList<>(taskService.getAllByUser(userId, loggedUser.getUser().getRole())));
    }

    private void checkIfOwn(final Integer userId, final UserDetailsImpl loggedUser) {
        if (!userId.equals(loggedUser.getUser().getId())) {
            throw new NotOwnedException("Requested user id is not consistent with logged user id");
        }
    }

}
