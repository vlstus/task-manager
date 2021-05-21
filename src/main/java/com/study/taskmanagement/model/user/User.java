package com.study.taskmanagement.model.user;

import com.study.taskmanagement.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User
        extends BaseEntity {

    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
