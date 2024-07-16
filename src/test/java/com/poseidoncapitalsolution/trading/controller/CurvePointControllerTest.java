package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import com.poseidoncapitalsolution.trading.service.contracts.ICurvePointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICurvePointService curvePointService;

    private CurvePoint anyCurvePoint = new CurvePoint(1, 1d, 1d);
    private List<CurvePoint> curvePoints = new ArrayList<>(Arrays.asList(anyCurvePoint, anyCurvePoint, anyCurvePoint));

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void listCurvePoints_returnOk() throws Exception {
        when(curvePointService.findAll()).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addForm_returnOk() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateForm_returnOk() throws Exception {
        when(curvePointService.findById(anyInt())).thenReturn(anyCurvePoint);

        mockMvc.perform(get("/curvePoint/update/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void add_successAndRedirectToListPage() throws Exception {
        when(curvePointService.findAll()).thenReturn(curvePoints);

        mockMvc.perform(post("/curvePoint/validate")
                        .flashAttr("curvePoint", anyCurvePoint)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));

        verify(curvePointService, times(1)).save(any(CurvePoint.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void add_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void update_successAndRedirectToListPage() throws Exception {
        when(curvePointService.findAll()).thenReturn(curvePoints);

        mockMvc.perform(post("/curvePoint/update/{id}", "1")
                        .flashAttr("curvePoint", anyCurvePoint)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));

        verify(curvePointService, times(1)).update(anyInt(), any(CurvePoint.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void update_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void delete_successAndRedirectToListPage() throws Exception {
        when(curvePointService.findAll()).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));

        verify(curvePointService, times(1)).deleteById(anyInt());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void findByCurveId_returnOk() throws Exception {
        when(curvePointService.findByCurveId(anyInt())).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/findByCurveId/{curveId}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));
    }
}
