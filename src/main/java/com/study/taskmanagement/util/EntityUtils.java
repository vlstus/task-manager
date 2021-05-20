package com.study.taskmanagement.util;

import com.study.taskmanagement.model.BaseEntity;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Predicate;

@UtilityClass
public class EntityUtils {

    public <Entity extends BaseEntity> void assurePersisted(Entity user) {
        assureThat(user, EntityUtils::exists);
    }

    public <Entity extends BaseEntity> void assureNew(Entity entity) {
        assureThat(entity, EntityUtils::isNew);
    }

    private <Entity extends BaseEntity> void assureThat(Entity entity, Predicate<Entity> condition) {
        if (condition.negate().test(entity))
            throw new RuntimeException();
    }

    public  <Entity extends BaseEntity> boolean exists(Entity entity) {
        return !isNew(entity);
    }

    public  <Entity extends BaseEntity> boolean isNew(Entity entity) {
        return Objects.isNull(entity.getId());
    }

}
