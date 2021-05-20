package com.study.taskmanagement.service;

import com.study.taskmanagement.model.BaseEntity;

public interface CrudService<T extends BaseEntity, ID> {

    T create(T entity);

    T update(T entity);

    T get(ID id);

    void delete(T entity);

}
