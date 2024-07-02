package controller;

import com.poseidoncapitalsolution.trading.controller.BidController;
import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BidControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IBidService bidService;

    @InjectMocks
    private BidController bidController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bidController).build();
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void testGetList() throws Exception {
        List<Bid> mockBids = new ArrayList<>();
        Bid bid1 = createValidBid();
        Bid bid2 = createValidBid();
        mockBids.add(bid1);
        mockBids.add(bid2);

        when(bidService.findAll()).thenReturn(mockBids);

        mockMvc.perform(get("/bid/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/list"))
                .andExpect(model().attributeExists("bids"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void testAddBid_ValidationSuccess() throws Exception {
        Bid validBid = createValidBid();

        mockMvc.perform(post("/bid/validate")
                        .flashAttr("bid", validBid))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bid/list"));

        verify(bidService, times(1)).save(validBid);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void testAddBid_ValidationError() throws Exception {
        Bid invalidBid = createInvalidBid(); // Provide an invalid Bid here to test validation errors

        mockMvc.perform(post("/bid/validate")
                        .flashAttr("bid", invalidBid))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/add"))
                .andExpect(model().attributeExists("bid"))
                .andExpect(model().hasErrors());

        verify(bidService, never()).save(any(Bid.class));
    }

    // Helper methods to create valid and invalid Bid objects for testing
    private Bid createValidBid() {
        Bid bid = new Bid();
        bid.setAccount("Test Account");
        bid.setType("Test Type");
        bid.setBidQuantity(100.0);
        bid.setBidListDate(new Timestamp(System.currentTimeMillis()));
        return bid;
    }

    private Bid createInvalidBid() {
        Bid bid = new Bid(); // Empty Bid with mandatory fields missing
        return bid;
    }
}
