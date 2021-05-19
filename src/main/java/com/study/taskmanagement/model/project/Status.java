package com.study.taskmanagement.model.project;


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
@Table(name = "statuses")
public class Status
        extends BaseEntity {

    enum Type {
        TO_DO, IN_PROGRESS, DONE
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type statusType;

}
