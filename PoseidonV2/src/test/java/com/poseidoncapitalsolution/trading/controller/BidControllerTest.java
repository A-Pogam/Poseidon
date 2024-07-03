package com.poseidoncapitalsolution.trading.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.repository.contracts.BidRepository;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

@WebMvcTest(controllers = BidController.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBidService bidService;

    @MockBean
    private BidRepository bidRepository;

    private Bid anyBid = new Bid(1, "Account", "Type", 1d);
    private List<Bid> bids = new ArrayList<>(Arrays.asList(anyBid, anyBid, anyBid));

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void getBids_returnOk() throws Exception {
        when(bidService.findAll()).thenReturn(bids);

        mockMvc.perform(get("/bid/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/list"))
                .andExpect(model().attributeExists("bids"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void getBidAddForm_returnOk() throws Exception {
        mockMvc.perform(get("/bid/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/add"))
                .andExpect(model().attributeExists("bid"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void getBidUpdateForm_returnOk() throws Exception {
        when(bidService.findById(anyInt())).thenReturn(anyBid);

        mockMvc.perform(get("/bid/update/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/update"))
                .andExpect(model().attributeExists("bid"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void getBidUpdateForm_throwNoContent() throws Exception {
        when(bidService.findById(anyInt())).thenReturn(null);

        mockMvc.perform(get("/bid/update/{id}", "0"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void postBidFromBidAddForm_successAndRedirectToListPage() throws Exception {
        doNothing().when(bidService).save(any(Bid.class));
        when(bidService.findAll()).thenReturn(bids);

        mockMvc.perform(post("/bid/validate")
                        .flashAttr("bid", anyBid)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bid/list"));

        verify(bidService, times(1)).save(any(Bid.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void postBidFromBidAddForm_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/bid/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/add"))
                .andExpect(model().attributeExists("bid"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void postBidFromBidUpdateForm_successAndRedirectToListPage() throws Exception {
        doNothing().when(bidService).update(anyInt(), any(Bid.class));
        when(bidService.findAll()).thenReturn(bids);

        mockMvc.perform(post("/bid/update/{id}", "1")
                        .flashAttr("bid", anyBid)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bid/list"));

        verify(bidService, times(1)).update(anyInt(), any(Bid.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void postBidFromBidUpdateForm_failAndReturnOk() throws Exception {
        Bid invalidBid = new Bid();
        invalidBid.setAccount(null);
        invalidBid.setType(null);
        invalidBid.setBidQuantity(-1d);

        mockMvc.perform(post("/bid/update/{id}", "1")
                        .flashAttr("bid", invalidBid)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/update"))
                .andExpect(model().attributeExists("bid"));
    }


    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void deleteBid_successAndRedirectToListPage() throws Exception {
        doNothing().when(bidService).update(anyInt(), any(Bid.class));
        when(bidService.findAll()).thenReturn(bids);

        mockMvc.perform(post("/bid/update/{id}", "1")
                        .flashAttr("bid", anyBid)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bid/list"));

        verify(bidService, times(1)).update(anyInt(), any(Bid.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void deleteBid_throwNoContent() throws Exception {
        doThrow(new IllegalArgumentException("Bid not found")).when(bidService).deleteById(anyInt());

        mockMvc.perform(get("/bid/delete/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(bidService, times(1)).deleteById(anyInt());
    }
}
