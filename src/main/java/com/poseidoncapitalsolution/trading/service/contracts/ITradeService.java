package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.Trade;
import java.util.List;

public interface ITradeService {
	List<Trade> findAll();

	Trade findById(Integer id);

	Trade save(Trade trade);

	void update(Integer id, Trade trade);

	void deleteById(Integer id);

	void resetTradeTestTable();
}
