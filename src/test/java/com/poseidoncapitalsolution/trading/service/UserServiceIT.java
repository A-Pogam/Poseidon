package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.service.contracts.IUserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceIT {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public void fillUserTable() {
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setUsername("User" + i);
            user.setPassword(passwordEncoder.encode("password" + i));
            user.setRole("ADMIN");
            user.setFullname("Full Name " + i);
            userService.saveUser(user);
        }
    }

    @AfterAll
    public void resetUserTable() {
        userService.getAllUsers().forEach(user -> userService.deleteById(user.getId()));
    }

    @Test
    public void getUserByUsername_returnUser() {
        Optional<User> user = userService.findByUsername("User1");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("User1");
    }

    @Test
    public void getUserByUsername_returnEmpty() {
        Optional<User> user = userService.findByUsername("NonExistentUser");

        assertThat(user).isNotPresent();
    }

    @Test
    public void getAllUsers_returnUsers() {
        List<User> users = userService.getAllUsers();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    public void getUserById_returnUser() {
        Optional<User> user = userService.findById(1);

        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(1);
    }

    @Test
    public void getUserById_returnEmpty() {
        Optional<User> user = userService.findById(999);

        assertThat(user).isNotPresent();
    }

    @Test
    public void deleteUserById_deletesUser() {
        Integer userIdToDelete = 2;
        Optional<User> userBeforeDeletion = userService.findById(userIdToDelete);

        assertThat(userBeforeDeletion).isPresent();

        userService.deleteById(userIdToDelete);

        Optional<User> userAfterDeletion = userService.findById(userIdToDelete);
        assertThat(userAfterDeletion).isNotPresent();
    }
}
