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

import com.poseidoncapitalsolution.trading.model.Trade;
import com.poseidoncapitalsolution.trading.service.contracts.ITradeService;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeServiceIT {

	@Autowired
	private ITradeService iTradeService;
	
	@BeforeAll
	public void fillTradeTable() {
		for (int i = 1; i <= 3; i++) {
			iTradeService.save(new Trade(null, "Account" + i, "Type" + i, Double.valueOf(i)));
		}
	}
	
	@AfterAll
	public void resetTradeTable() {
		iTradeService.resetTradeTestTable();
	}
	
	@Test
	public void getTrades_returnTrades() {
		List<Trade> trades = iTradeService.findAll();
		
		assertThat(trades).isNotEmpty();
		assertThat(trades.getFirst().getTradeId()).isEqualTo(1);
		assertThat(trades.getFirst().getAccount()).isEqualTo("Account1");
		assertThat(trades.getFirst().getType()).isEqualTo("Type1");
		assertThat(trades.getFirst().getBuyQuantity()).isEqualTo(1d);
	}

	@Test
	public void getTradeById_returnTrade() {
		Trade trade = iTradeService.findById(1);
		
		assertThat(trade.getTradeId()).isEqualTo(1);
		assertThat(trade.getAccount()).isEqualTo("Account1");
		assertThat(trade.getType()).isEqualTo("Type1");
		assertThat(trade.getBuyQuantity()).isEqualTo(1d);
	}
	
	@Test
	public void getTradeById_returnNull() {
		Trade trade = iTradeService.findById(0);
		
		assertThat(trade).isEqualTo(null);
	}

	@Test
	public void saveTrade_returnTrade() {
		Trade newTrade = new Trade(null, "NewAccount", "NewType", 42d);
		
		Trade tradeAdded = iTradeService.save(newTrade);
		
		assertThat(tradeAdded.getAccount()).isEqualTo(newTrade.getAccount());
		assertThat(tradeAdded.getType()).isEqualTo(newTrade.getType());
		assertThat(tradeAdded.getBuyQuantity()).isEqualTo(newTrade.getBuyQuantity());
	}
	
	@Test
	public void updateTrade_returnUpdatedBid() {
	    Integer existingTradeId = 3;
	    Trade updatedTrade = new Trade(null, "UpdatedAccount", "UpdatedType", 33d);
	
	    iTradeService.update(existingTradeId, updatedTrade);
	
	    Trade trade = iTradeService.findById(existingTradeId);
	    assertThat(trade).isNotNull();
		assertThat(trade.getAccount()).isEqualTo(updatedTrade.getAccount());
		assertThat(trade.getType()).isEqualTo(updatedTrade.getType());
		assertThat(trade.getBuyQuantity()).isEqualTo(updatedTrade.getBuyQuantity());
	}
	
	@Test
	public void deleteTrade_deleteTrade() {
		Trade tradeToDelete = iTradeService.findById(2);
		
		iTradeService.deleteById(2);
		
		assertThat(!iTradeService.findAll().contains(tradeToDelete));
	}
}
