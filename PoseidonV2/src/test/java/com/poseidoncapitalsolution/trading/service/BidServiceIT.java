package com.poseidoncapitalsolution.trading.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidServiceIT {

    @Autowired
    private IBidService iBidService;

    @BeforeAll
    public void fillBidTable() {
        for (int i = 1; i <= 3; i++) {
            Bid bid = new Bid(null, "Account" + i, "Type" + i, Double.valueOf(i));
            iBidService.save(bid);
        }
    }

    @AfterAll
    public void resetBidTable() {
        iBidService.resetBidTestTable();
    }


    @Test
    public void getBidById_returnBid() {
        Bid bid = iBidService.findById(1);

        assertThat(bid).isNotNull();
        assertThat(bid.getAccount()).isEqualTo("Account1");
        assertThat(bid.getType()).isEqualTo("Type1");
        assertThat(bid.getBidQuantity()).isEqualTo(1d);
    }

    @Test
    public void getBidById_returnNull() {
        Bid bid = iBidService.findById(0);

        assertThat(bid).isNull();
    }

    @Test
    public void addOrUpdateBid_returnBid() {
        Bid newBid = new Bid(null, "NewAccount", "NewType", 42d);

        iBidService.save(newBid);

        List<Bid> bids = iBidService.findAll();
        Bid bidAdded = bids.stream()
                .filter(bid -> "NewAccount".equals(bid.getAccount()))
                .findFirst()
                .orElse(null);

        assertThat(bidAdded).isNotNull();
        assertThat(bidAdded.getAccount()).isEqualTo(newBid.getAccount());
        assertThat(bidAdded.getType()).isEqualTo(newBid.getType());
        assertThat(bidAdded.getBidQuantity()).isEqualTo(newBid.getBidQuantity());
    }

    @Test
    public void updateBid_returnUpdatedBid() {
        Integer existingBidId = 1;
        Bid updatedBid = new Bid(existingBidId, "UpdatedAccount", "UpdatedType", 100d);

        iBidService.update(existingBidId, updatedBid);

        Bid bid = iBidService.findById(existingBidId);
        assertThat(bid).isNotNull();
        assertThat(bid.getAccount()).isEqualTo("UpdatedAccount");
        assertThat(bid.getType()).isEqualTo("UpdatedType");
        assertThat(bid.getBidQuantity()).isEqualTo(100d);
    }

    @Test
    public void deleteBid_deleteBid() {
        Integer bidIdToDelete = 2;
        Bid bidToDelete = iBidService.findById(bidIdToDelete);

        assertThat(bidToDelete).isNotNull();

        iBidService.deleteById(bidIdToDelete);

        Bid deletedBid = iBidService.findById(bidIdToDelete);
        assertThat(deletedBid).isNull();
    }
}
