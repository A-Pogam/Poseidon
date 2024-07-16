package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Trade;
import com.poseidoncapitalsolution.trading.service.contracts.ITradeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeServiceIT {

    @Autowired
    private ITradeService iTradeService;

    @BeforeAll
    public void fillTradeTable() {
        for (int i = 1; i <= 3; i++) {
            Trade trade = new Trade();
            trade.setAccount("Account" + i);
            trade.setType("Type" + i);
            trade.setBuyQuantity(100.0 + i);
            iTradeService.save(trade);
        }
    }

    @AfterAll
    public void resetTradeTable() {
        iTradeService.findAll().forEach(trade -> iTradeService.deleteById(trade.getTradeId()));
    }

    @Test
    public void getTradeById_returnTrade() {
        Trade trade = iTradeService.findById(1);

        assertThat(trade).isNotNull();
        assertThat(trade.getAccount()).isEqualTo("Account1");
        assertThat(trade.getType()).isEqualTo("Type1");
        assertThat(trade.getBuyQuantity()).isEqualTo(101.0);
    }

    @Test
    public void getTradeById_returnNull() {
        Trade trade = iTradeService.findById(0);

        assertThat(trade).isNull();
    }

    @Test
    public void addOrUpdateTrade_returnTrade() {
        Trade newTrade = new Trade();
        newTrade.setAccount("NewAccount");
        newTrade.setType("NewType");
        newTrade.setBuyQuantity(42.0);

        iTradeService.save(newTrade);

        List<Trade> trades = iTradeService.findAll();
        Trade tradeAdded = trades.stream()
                .filter(trade -> "NewAccount".equals(trade.getAccount()))
                .findFirst()
                .orElse(null);

        assertThat(tradeAdded).isNotNull();
        assertThat(tradeAdded.getAccount()).isEqualTo(newTrade.getAccount());
        assertThat(tradeAdded.getType()).isEqualTo(newTrade.getType());
        assertThat(tradeAdded.getBuyQuantity()).isEqualTo(newTrade.getBuyQuantity());
    }

    @Test
    public void updateTrade_returnUpdatedTrade() {
        Integer existingTradeId = 1;
        Trade updatedTrade = new Trade();
        updatedTrade.setTradeId(existingTradeId);
        updatedTrade.setAccount("UpdatedAccount");
        updatedTrade.setType("UpdatedType");
        updatedTrade.setBuyQuantity(1000.0);

        iTradeService.update(existingTradeId, updatedTrade);

        Trade trade = iTradeService.findById(existingTradeId);
        assertThat(trade).isNotNull();
        assertThat(trade.getAccount()).isEqualTo("UpdatedAccount");
        assertThat(trade.getType()).isEqualTo("UpdatedType");
        assertThat(trade.getBuyQuantity()).isEqualTo(1000.0);
    }

    @Test
    public void deleteTrade_deleteTrade() {
        Integer tradeIdToDelete = 2;
        Trade tradeToDelete = iTradeService.findById(tradeIdToDelete);

        assertThat(tradeToDelete).isNotNull();

        iTradeService.deleteById(tradeIdToDelete);

        Trade deletedTrade = iTradeService.findById(tradeIdToDelete);
        assertThat(deletedTrade).isNull();
    }
}
