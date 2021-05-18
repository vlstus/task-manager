package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest
        extends BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void getById()
            throws Exception {
        assertDoesNotThrow(() -> {
            User user = userRepository.findById(100_000).get();
            assertEquals("John Doe", user.getName());
            assertEquals(0, user.getRole());
        });

    }

    @Test
    @DirtiesContext
    void create() {
        final String newUserName = "New User";
        User newUser = new User();
        newUser.setName(newUserName);
        newUser.setPassword("New Password");
        newUser.setRole(1);
        User persisted = userRepository.findById(userRepository.save(newUser).getId()).get();
        assertEquals("New User", persisted.getName());
    }

    @Test
    @DirtiesContext
    void delete() {
        final int testUserId = 100_000;
        User userToDelete = userRepository.findById(testUserId).get();
        userRepository.delete(userToDelete);
        assertFalse(userRepository.findById(testUserId).isPresent());
    }

    @Test
    @DirtiesContext
    void update() {
        final String updatedName = "Updated Name";
        User userToUpdate = userRepository.findById(100_000).get();
        userToUpdate.setName(updatedName);
        User updatedUser = userRepository.save(userToUpdate);
        assertEquals(updatedName, updatedUser.getName());
    }

}