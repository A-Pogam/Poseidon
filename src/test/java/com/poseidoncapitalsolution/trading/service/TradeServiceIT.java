package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Trade;
import com.poseidoncapitalsolution.trading.repository.contracts.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceIT {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);
    }

    @Test
    public void testFindAll() {
        List<Trade> trades = Arrays.asList(trade, new Trade());
        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> result = tradeService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertNotNull(result);
        assertEquals(trade.getTradeId(), result.getTradeId());
        verify(tradeRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testFindById_NotFound() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        Trade result = tradeService.findById(1);

        assertNull(result);
        verify(tradeRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testSave() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade result = tradeService.save(trade);

        assertNotNull(result);
        assertEquals(trade.getTradeId(), result.getTradeId());
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void testUpdate() {
        tradeService.update(1, trade);

        verify(tradeRepository, times(1)).save(trade);
        assertEquals(1, trade.getTradeId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(tradeRepository).deleteById(anyInt());

        tradeService.deleteById(1);

        verify(tradeRepository, times(1)).deleteById(anyInt());
    }
}
