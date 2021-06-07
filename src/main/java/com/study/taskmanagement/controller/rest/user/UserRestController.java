package com.study.taskmanagement.controller.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.security.UserDetailsImpl;
import com.study.taskmanagement.service.exception.NotOwnedException;
import com.study.taskmanagement.service.project.ProjectService;
import com.study.taskmanagement.service.task.TaskService;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
class UserRestController
        extends AbstractRestController<User, Integer> {

    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;

    protected UserRestController(UserService userService,
                                 ProjectService projectService,
                                 TaskService taskService) {
        super(userService);
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping(params = {"role"})
    public List<User> getByRole(@RequestParam("role") Role role) {
        UserService userService = (UserService) crudService;
        return new ArrayList<>(userService.getByRole(role));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Collection<User>> getAll() {
        return super.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return super.getById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<User> create(@Valid @RequestBody User entity) {
        return super.create(entity);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@Valid @RequestBody User entity, @PathVariable Integer id) {
        super.update(entity, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Override
    public void delete(@PathVariable Integer id) {
        super.delete(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(path = "/{userId}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable Integer userId,
                                                     @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        checkIfOwn(userId, loggedUser);
        return ResponseEntity.ok(new ArrayList<>(projectService.getAllByUserId(userId)));
    }

    @PreAuthorize("hasAnyRole('DEVELOPER','MANAGER')")
    @GetMapping(path = "/{userId}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Integer userId,
                                               @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        checkIfOwn(userId, loggedUser);
        return ResponseEntity.ok(new ArrayList<>(taskService.getAllByUser(userId, loggedUser.getUser().getRole())));
    }

    private void checkIfOwn(Integer userId, UserDetailsImpl loggedUser) {
        if (!userId.equals(loggedUser.getUser().getId())) {
            throw new NotOwnedException("Requested user id is not consistent with logged user id");
        }
    }

}
