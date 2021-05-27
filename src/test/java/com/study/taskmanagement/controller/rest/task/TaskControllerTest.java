package com.study.taskmanagement.controller.rest.task;

import com.study.taskmanagement.controller.BaseControllerTest;
import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.repository.project.ProjectTestData;
import com.study.taskmanagement.repository.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskControllerTest
        extends BaseControllerTest {

    @Test
    void getByIdTest()
            throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/tasks/{id}",
                ProjectTestData.TaskTestData.TEST_TASK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(ProjectTestData.TaskTestData.TEST_TASK),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(
                        JSONCompareMode.LENIENT,
                        new Customization("project", (first, second) -> true)));
    }

    @Test
    void createTest()
            throws Exception {
        final Task task = new Task("New", UserTestData.TEST_MANAGER, Status.TO_DO, UserTestData.TEST_DEVELOPER, ProjectTestData.TEST_PROJECT);
        final MvcResult mvcResult = mockMvc.perform(post("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(task),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("id", (first, second) -> true)));
    }

    @Test
    void getAllTest()
            throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/tasks/"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(Collections.singletonList(ProjectTestData.TaskTestData.TEST_TASK)),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(
                        JSONCompareMode.LENIENT,
                        new Customization("[id=100000].project", (first, second) -> true)));
    }

    @Test
    void updateTest()
            throws Exception {
        final Task project = ProjectTestData.TaskTestData.copyOf(ProjectTestData.TaskTestData.TEST_TASK);
        project.setName("New");
        mockMvc.perform(put("/api/v1/tasks/{id}", project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTest()
            throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProjectTestData.TEST_PROJECT)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
