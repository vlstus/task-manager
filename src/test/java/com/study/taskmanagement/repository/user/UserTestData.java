package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UserTestData {

    public static final int TEST_DEVELOPER_ID = 100_000;
    public static final User TEST_DEVELOPER = new User("John Doe", "Password", Role.ofType("DEVELOPER"));
    public static final User TEST_MANAGER = new User("Jane Doe", "Password", Role.ofType("MANAGER"));

    public static final List<User> TEST_USERS = new ArrayList<>(Arrays.asList(TEST_DEVELOPER, TEST_MANAGER));

    public static final String ADMIN_NAME = "Joe Doe";
    public static final String UPDATED_NAME = "Jim Beam";

    public static User getNew() {
        return new User(ADMIN_NAME, "New", Role.ofType("ADMIN"));
    }

    public static User getUpdatedDeveloper() {
        User updated = new User(UPDATED_NAME, TEST_DEVELOPER.getPassword(), TEST_DEVELOPER.getRole());
        updated.setId(TEST_DEVELOPER_ID);
        return updated;
    }

}
