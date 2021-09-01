package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.controller.BaseControllerTest;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest
        extends BaseControllerTest {

    @Test
    void getByIdTest()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/{id}", UserTestData.TEST_DEVELOPER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(UserTestData.copyOf(UserTestData.TEST_DEVELOPER)),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("id", (first, second) -> true),
                        new Customization("password", (first, second) -> true)));
    }

    @Test
    void createTest()
            throws Exception {
        final User user = new User("NewUser", "Password", Role.ROLE_ADMIN);
        final MvcResult mvcResult = mockMvc.perform(post("/api/v1/users/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(user),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("id", (first, second) -> true),
                        new Customization("password", (first, second) -> true)));
    }

    @Test
    void getAllTest()
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users/"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(UserTestData.TEST_USERS),
                mvcResult.getResponse().getContentAsString(),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        new Customization("[*].id", (first, second) -> true),
                        new Customization("[*].password", (first, second) -> true)));
    }

    @Test
    void updateTest()
            throws Exception {
        final User user = UserTestData.copyOf(UserTestData.TEST_MANAGER);
        user.setName("NewName");
        mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTest()
            throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", UserTestData.TEST_DEVELOPER_ID)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
