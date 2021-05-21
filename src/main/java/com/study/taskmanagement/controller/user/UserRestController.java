package com.study.taskmanagement.controller.user;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import com.study.taskmanagement.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users/")
class UserRestController {

    @Autowired
    UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(
            path = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    User getById(@PathVariable Integer id) {
        try {
            return userService.get(id);
        } catch (BusinessLayerException businessException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MESSAGE_SOURCE.NOT_FOUND_MESSAGE", businessException);
        }
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> create(@RequestBody User user) {
        final User createdUser = userService.create(user);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdUser.getId())
                .toUri())
                .body(createdUser);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody User user, @PathVariable Integer id) {
        try {
            userService.update(user, id);
        } catch (BusinessLayerException businessException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MESSAGE_SOURCE.BAD_UPDATE_DTO", businessException);
        }
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestBody User user) {
        userService.delete(user);
    }

}
