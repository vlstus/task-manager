package com.study.taskmanagement.controller.rest;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.service.CrudService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

public abstract class AbstractRestController<T extends BaseEntity, ID> {

    @Autowired
    protected final CrudService<T, ID> crudService;

    protected AbstractRestController(CrudService<T, ID> crudService) {
        this.crudService = crudService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<T>> getAll() {
        return ResponseEntity.ok(crudService.getAll());
    }

    @GetMapping(
            path = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getById(@PathVariable ID id) {
        try {
            return ResponseEntity.ok(crudService.get(id));
        } catch (BusinessLayerException businessException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MESSAGE_SOURCE.NOT_FOUND_MESSAGE", businessException);
        }
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> create(@RequestBody T dto) {
        final T created = crudService.create(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(created.getId())
                .toUri())
                .body(created);
    }

    @PutMapping(
            path = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody T dto, @PathVariable ID id) {
        try {
            crudService.update(dto, id);
        } catch (BusinessLayerException businessException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MESSAGE_SOURCE.BAD_UPDATE_DTO", businessException);
        }
    }

    //    @DeleteMapping(
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@RequestBody T dto) {
//        crudService.delete(dto);
//    }
    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable ID id) {
        crudService.delete(crudService.get(id));
    }

}
