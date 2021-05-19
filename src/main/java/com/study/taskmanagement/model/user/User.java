package com.study.taskmanagement.model.user;

import com.study.taskmanagement.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class User
        extends BaseEntity {

    private String name;
    private String password;
    private Role role;

}
