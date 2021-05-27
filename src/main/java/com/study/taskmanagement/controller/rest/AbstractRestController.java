package com.study.taskmanagement.controller.rest;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.service.CrudService;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

public abstract class AbstractRestController<T extends BaseEntity, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected final CrudService<T, ID> crudService;

    protected AbstractRestController(CrudService<T, ID> crudService) {
        this.crudService = crudService;
    }

    public ResponseEntity<Collection<T>> getAll() {
        log.info("Getting all entities with controller of type : {}", getClass().getSimpleName());
        return ResponseEntity.ok(crudService.getAll());
    }

    public ResponseEntity<T> getById(ID id) {
        log.info("Getting entity with id : {} with controller of type : {}",
                id, getClass().getSimpleName());
        try {
            return ResponseEntity.ok(crudService.get(id));
        } catch (BusinessLayerException businessException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MESSAGE_SOURCE.NOT_FOUND_MESSAGE", businessException);
        }
    }

    public ResponseEntity<T> create(T entity) {
        log.info("Creating entity : {} with controller of type : {}",
                entity, getClass().getSimpleName());
        final T created = crudService.create(entity);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri())
                .body(created);
    }

    public void update(T entity, ID id) {
        log.info("Updating entity : {} with controller of type : {}",
                entity, getClass().getSimpleName());
        try {
            crudService.update(entity, id);
        } catch (BusinessLayerException businessException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MESSAGE_SOURCE.BAD_UPDATE_DTO", businessException);
        }
    }

    public void delete(ID id) {
        log.info("Deleting entity with id : {} with controller of type : {}",
                id, getClass().getSimpleName());
        crudService.delete(crudService.get(id));
    }

}
