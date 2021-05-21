package com.study.taskmanagement.controller.project;

import com.study.taskmanagement.controller.BaseControllerTest;
import com.study.taskmanagement.model.project.Project;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectControllerTest
        extends BaseControllerTest {

    @Test
    void getByIdTest()
            throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/projects/{id}", ProjectTestData.TEST_PROJECT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(ProjectTestData.TEST_PROJECT),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(
                        JSONCompareMode.LENIENT,
                        new Customization("tasks", (first, second) -> true)));
    }

    @Test
    void createTest()
            throws Exception {
        final Project project = new Project("New", UserTestData.TEST_MANAGER, null);
        final MvcResult mvcResult = mockMvc.perform(post("/api/v1/projects/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(project),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("id", (first, second) -> true)));
    }

    @Test
    void getAllTest()
            throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/projects/"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(Collections.singletonList(ProjectTestData.TEST_PROJECT)),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(
                        JSONCompareMode.LENIENT,
                        new Customization("[id=100000].tasks", (first, second) -> true)));
    }

    @Test
    void updateTest()
            throws Exception {
        final Project project = ProjectTestData.copyOf(ProjectTestData.TEST_PROJECT);
        project.setName("New");
        mockMvc.perform(put("/api/v1/projects/{id}", project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTest()
            throws Exception {
        mockMvc.perform(delete("/api/v1/projects/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProjectTestData.TEST_PROJECT)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
