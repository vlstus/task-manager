package com.study.taskmanagement.service;

import com.study.taskmanagement.model.BaseEntity;

import java.util.Collection;

public interface CrudService<T extends BaseEntity, ID> {

    T create(T entity);

    T update(T entity, ID entityId);

    T get(ID id);

    Collection<T> getAll();

    void delete(T entity);

}
