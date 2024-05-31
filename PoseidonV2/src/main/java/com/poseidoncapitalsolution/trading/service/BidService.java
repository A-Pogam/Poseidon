package com.poseidoncapitalsolution.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidoncapitalsolution.trading.repository.contracts.BidRepository;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;

@Service
public class BidService implements IBidService {

	@Autowired
	BidRepository bidRepository;
	
	@Override
	public void resetBidTestTable() {
		bidRepository.resetBidTestTable();
	}
}