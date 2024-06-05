package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.Bid;
import java.util.List;

public interface IBidService {
	List<Bid> findAll();
	Bid findById(Integer id);
	void save(Bid bid);
	void update(Integer id, Bid bid);
	void deleteById(Integer id);
	void resetBidTestTable();
}
