package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.service.contracts.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private User anyUser = new User(1, "username", "password", "test", "ROLE_USER");

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetUserList() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(anyUser);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testAddUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testPostUser_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .flashAttr("user", anyUser)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testPostUser_failAndReturnAddPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));

    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testShowUpdateForm_success() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(anyUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }


    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testPutUserForUserUpdate_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", 1)
                        .flashAttr("user", anyUser)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));

        verify(userService, times(1)).saveUser(any(User.class));
    }


    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testDeleteById_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));

        verify(userService, times(1)).deleteById(1);
    }
}
