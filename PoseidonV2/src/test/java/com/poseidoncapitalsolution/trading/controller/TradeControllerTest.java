package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.Trade;
import com.poseidoncapitalsolution.trading.service.contracts.ITradeService;
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
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITradeService tradeService;

    private Trade anyTrade = new Trade(1, "A", "B", 1d);

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getList_returnOk() throws Exception {
        List<Trade> trades = new ArrayList<>(Arrays.asList(anyTrade, anyTrade, anyTrade));
        when(tradeService.findAll()).thenReturn(trades);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addTrade() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getUpdateForm_returnOk() throws Exception {
        when(tradeService.findById(anyInt())).thenReturn(anyTrade);

        mockMvc.perform(get("/trade/update/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addTrade_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .flashAttr("trade", anyTrade)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));

        verify(tradeService, times(1)).save(any(Trade.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void addTrade_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateTrade_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", "1")
                        .flashAttr("trade", anyTrade)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));

        verify(tradeService, times(1)).update(anyInt(), any(Trade.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updateTrade_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deleteTrade_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));

        verify(tradeService, times(1)).deleteById(anyInt());
    }
}
