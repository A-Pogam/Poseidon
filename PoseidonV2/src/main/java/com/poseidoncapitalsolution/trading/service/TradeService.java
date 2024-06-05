package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Trade;
import com.poseidoncapitalsolution.trading.repository.contracts.TradeRepository;
import com.poseidoncapitalsolution.trading.service.contracts.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService implements ITradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(Integer id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        return optionalTrade.orElse(null);
    }

    @Override
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public void update(Integer id, Trade trade) {
        trade.setTradeId(id);
        tradeRepository.save(trade);
    }

    @Override
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
