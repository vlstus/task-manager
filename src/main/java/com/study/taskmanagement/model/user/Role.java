package com.study.taskmanagement.model.user;

import com.study.taskmanagement.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role
        extends BaseEntity {

    public enum Type {
        ADMIN, DEVELOPER, MANAGER
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type roleType;

    public static Role ofType(String typeName) {
        return new Role(Type.valueOf(typeName));
    }

}
