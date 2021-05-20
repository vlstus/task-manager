package com.study.taskmanagement.service;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.service.exception.BusinessLayerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

@Slf4j
public abstract class AbstractService<T extends BaseEntity, ID>
        implements CrudService<T, ID> {

    private final CrudRepository<T, ID> crudRepository;

    protected AbstractService(CrudRepository<T, ID> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public T create(T entity) {
        log.info("Creating entity {}", entity);
        return crudRepository.save(entity);
    }

    @Override
    public T update(T entity) {
        log.info("Updating entity {}", entity);
        if (entity.getId() == null) {
            log.warn("Failed to update entity {}", entity);
            throw new BusinessLayerException();
        }
        return crudRepository.save(entity);
    }

    @Override
    public T get(ID id) {
        log.info("Getting entity with id {}", id);
        return crudRepository.findById(id)
                .orElseThrow(BusinessLayerException::new);
    }

    @Override
    public void delete(T entity) {
        log.info("Deleting entity {}", entity);
        crudRepository.delete(entity);
    }

}
