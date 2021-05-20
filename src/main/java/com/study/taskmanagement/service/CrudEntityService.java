package com.study.taskmanagement.service;

public interface CrudEntityService<Entity, Identifier> {

    Entity create(Entity entity);

    Entity update(Entity entity);

    Entity get(Identifier identifier);

    void delete(Entity entity);

}
