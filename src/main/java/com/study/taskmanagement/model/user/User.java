package com.study.taskmanagement.model.user;

import com.study.taskmanagement.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User
        extends BaseEntity {

    @Column
    private String name;
    @Column
    private String password;
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
