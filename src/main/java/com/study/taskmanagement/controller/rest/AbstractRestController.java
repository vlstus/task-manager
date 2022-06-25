package com.study.taskmanagement.controller.rest;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class AbstractRestController<Model extends BaseEntity, TransferObject, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final CrudService<Model, ID> crudService;
    protected final MappingService<Model, TransferObject> mappingService;

    public ResponseEntity<Collection<TransferObject>> getAll() {
        log.info("Getting all entities with controller of type : {}", getClass().getSimpleName());
        return ResponseEntity.ok(crudService.getAll()
                .stream()
                .map(mappingService::convertToTransferObject)
                .collect(toList()));
    }

    public ResponseEntity<TransferObject> getById(ID id) {
        log.info("Getting entity with id : {} with controller of type : {}",
                id, getClass().getSimpleName());
        return ResponseEntity.ok(mappingService.convertToTransferObject(crudService.get(id)));
    }

    public ResponseEntity<TransferObject> create(TransferObject transferObject) {
        log.info("Creating entity : {} with controller of type : {}",
                transferObject, getClass().getSimpleName());
        final Model created = crudService.create(mappingService.convertToModel(transferObject));
        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(created.getId())
                        .toUri())
                .body(mappingService.convertToTransferObject(created));
    }

    public void update(TransferObject transferObject, ID id) {
        log.info("Updating entity : {} with controller of type : {}",
                transferObject, getClass().getSimpleName());
        crudService.update(mappingService.convertToModel(transferObject), id);
    }

    public void delete(ID id) {
        log.info("Deleting entity with id : {} with controller of type : {}",
                id, getClass().getSimpleName());
        crudService.delete(crudService.get(id));
    }

}
