package com.study.taskmanagement.controller.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;

import java.io.IOException;

public class NewTaskDeserializer
        extends StdDeserializer<Task> {

    public NewTaskDeserializer(Class<?> vc) {
        super(vc);
    }

    protected NewTaskDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected NewTaskDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Task deserialize(JsonParser p,
                            DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        final JsonNode idNode = node.get("id");
        Integer id = null;
        if (idNode != null &&
                !idNode.asText().isEmpty()) {
            id = idNode.asInt();
        }
        final String taskName = node.get("name").asText();
        final String status = node.get("status").asText();
        final String managerName = node.get("manager").get("name").asText();
        final String developerName = node.get("developer").get("name").asText();
        final String projectName = node.get("project").get("name").asText();
        final Task task = new Task(taskName, new User(managerName, null, Role.MANAGER), Status.valueOf(status), new User(developerName, null, Role.DEVELOPER), new Project(projectName, new User(managerName, null, Role.MANAGER), null));
        if (id != null) {
            task.setId(id);
        }
        return task;
    }

}
