package com.study.taskmanagement.service;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.service.exception.NotFoundException;
import com.study.taskmanagement.service.exception.NotOwnedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractService<T extends BaseEntity, ID>
        implements CrudService<T, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final CrudRepository<T, ID> crudRepository;

    protected AbstractService(CrudRepository<T, ID> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public T create(T entity) {
        log.info("Creating entity {}", entity);
        return crudRepository.save(entity);
    }

    @Override
    @Transactional
    public T update(T entity, ID entityId) {
        log.info("Updating entity {}", entity);
        if (entity.getId() == null ||
                !(entity.getId().equals(entityId)) ||
                !crudRepository.existsById(entityId)) {
            log.warn("Failed to update entity {}", entity);
            throw new NotOwnedException();
        }
        return crudRepository.save(entity);
    }

    @Override
    public T get(ID id) {
        log.info("Getting entity with id {}", id);
        return crudRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }

    @Override
    public Collection<T> getAll() {
        log.info("Getting all entities in service of type {}",
                getClass().getSimpleName());
        return StreamSupport.stream(crudRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(T entity) {
        log.info("Deleting entity {}", entity);
        crudRepository.delete(entity);
    }

}
