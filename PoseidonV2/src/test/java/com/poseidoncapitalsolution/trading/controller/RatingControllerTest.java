package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.Rating;
import com.poseidoncapitalsolution.trading.service.contracts.IRatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRatingService ratingService;

    private Rating anyRating = new Rating(1, "AAA", "BBB", "CCC", 1);
    private List<Rating> ratings = new ArrayList<>(Arrays.asList(anyRating, anyRating, anyRating));

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getList_returnOk() throws Exception {
        when(ratingService.findAll()).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ratings"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addRating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getUpdateForm_returnOk() throws Exception {
        when(ratingService.findById(anyInt())).thenReturn(anyRating);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addRating_successAndRedirectToListPage() throws Exception {
        when(ratingService.findAll()).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .flashAttr("rating", anyRating)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addRating_failAndReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateRating_successAndRedirectToListPage() throws Exception {
        when(ratingService.findAll()).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", "1")
                        .flashAttr("rating", anyRating)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/rating/list"));

        verify(ratingService, times(1)).update(anyInt(), any(Rating.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateRating_failAndReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", "1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deleteRating_successAndRedirectToListPage() throws Exception {
        when(ratingService.findAll()).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/rating/list"));

        verify(ratingService, times(1)).deleteById(anyInt());
    }
}
