package com.study.taskmanagement.controller;

import com.study.taskmanagement.model.BaseEntity;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface CrudRestController<T extends BaseEntity, ID> {

    Collection<T> getAll();

    T getById(ID id);

    ResponseEntity<T> create(T dto);

    void update(T dto, ID id);

    void delete(T dto);

}
