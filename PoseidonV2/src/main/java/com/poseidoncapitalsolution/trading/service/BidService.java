package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.repository.contracts.BidRepository;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidService implements IBidService {

	@Autowired
	private BidRepository bidRepository;

	@Override
	public List<Bid> findAll() {
		List<Bid> bids;
		bids = bidRepository.findAll();
		if (bids == null) {
			bids = new ArrayList<>();
		}
		return bids;
	}

	@Override
	public void save(Bid bid) {
		bidRepository.save(bid);
	}

	@Override
	public Bid findById(Integer id) {
		Optional<Bid> optionalBid = bidRepository.findById(id);
		return optionalBid.orElse(null);
	}

	@Override
	public void update(Integer id, Bid bid) {
		bid.setBidListId(id);
		bidRepository.save(bid);
	}

	@Override
	public void deleteById(Integer id) {
		bidRepository.deleteById(id);
	}

	@Override
	public void resetBidTestTable() {
		bidRepository.resetBidTestTable();
	}

}
