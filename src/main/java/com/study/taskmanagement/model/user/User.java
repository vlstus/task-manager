package com.study.taskmanagement.model.user;

import com.study.taskmanagement.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User
        extends BaseEntity {

    @NotEmpty
    @Length(min = 4, max = 25)
    private String name;
    @Length(min = 5, max = 100)
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

}
