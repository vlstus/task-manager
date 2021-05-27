package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.controller.rest.AbstractRestController;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    protected UserRestController(UserService userService) {
        super(userService);
    }

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

}
