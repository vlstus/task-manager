package com.study.taskmanagement.model.user;

import com.study.taskmanagement.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role
        extends BaseEntity {

    enum Type {
        ADMIN, DEVELOPER, MANAGER
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type roleType;

}
