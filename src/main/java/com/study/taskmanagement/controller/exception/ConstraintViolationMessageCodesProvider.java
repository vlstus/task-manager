package com.study.taskmanagement.controller.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConstraintViolationMessageCodesProvider {

    private final Map<String, String> constraintsCodes = new HashMap<String, String>() {
        {
            put("PUBLIC.UNIQUE_USERNAME_IDX", "application.error.users.unique");
            put("PUBLIC.UNIQUE_PROJECTNAME_IDX", "application.error.projects.unique");
            put("PUBLIC.UNIQUE_TASKNAME_IDX", "application.error.tasks.unique");
        }
    };

    private String getCode(String constraint) {
        return constraintsCodes.get(constraint.split(" ")[0].replaceAll("\"", ""));
    }

    public String getCode(ConstraintViolationException constraintViolationException) {
        return getCode(constraintViolationException.getConstraintName());
    }

}
