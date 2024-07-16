package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.repository.contracts.BidRepository;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class implementing the {@link IBidService} interface to provide business logic for managing Bid entities.
 * <p>
 * This class interacts with the data layer via the {@link BidRepository} to perform CRUD operations.
 * </p>
 *
 * @author Alice Pogam
 * @version 1.0
 */
@Service
public class BidService implements IBidService {

	@Autowired
	private BidRepository bidRepository;

	/**
	 * Retrieves all Bid entities from the database.
	 * <p>
	 * If no bids are found, returns an empty list.
	 * </p>
	 *
	 * @return a list of all bids or an empty list if no bids are found.
	 */
	@Override
	public List<Bid> findAll() {
		List<Bid> bids = bidRepository.findAll();
		return (bids != null) ? bids : new ArrayList<>();
	}

	/**
	 * Saves a new Bid entity to the database or updates an existing one.
	 * <p>
	 * If the bid already exists (has a non-null ID), it updates the existing record; otherwise, it creates a new one.
	 * </p>
	 *
	 * @param bid the Bid entity to be saved or updated.
	 */
	@Override
	public void save(Bid bid) {
		bidRepository.save(bid);
	}

	/**
	 * Retrieves a specific Bid by its unique identifier.
	 * <p>
	 * If no bid is found with the given ID, returns null.
	 * </p>
	 *
	 * @param id the unique identifier of the bid.
	 * @return the Bid entity if found, otherwise null.
	 */
	@Override
	public Bid findById(Integer id) {
		Optional<Bid> optionalBid = bidRepository.findById(id);
		return optionalBid.orElse(null);
	}

	/**
	 * Updates an existing Bid entity identified by its ID.
	 * <p>
	 * This method sets the ID of the provided Bid entity to the given ID and then saves it, effectively updating the record.
	 * </p>
	 *
	 * @param id the unique identifier of the bid to be updated.
	 * @param bid the Bid entity with updated information.
	 */
	@Override
	public void update(Integer id, Bid bid) {
		bid.setBidListId(id);
		bidRepository.save(bid);
	}

	/**
	 * Deletes a Bid entity by its unique identifier.
	 *
	 * @param id the unique identifier of the bid to be deleted.
	 */
	@Override
	public void deleteById(Integer id) {
		bidRepository.deleteById(id);
	}

	/**
	 * Resets the Bid table to its initial state.
	 * <p>
	 * This method is typically used to clear or reset the table for testing purposes.
	 * </p>
	 */
	@Override
	public void resetBidTestTable() {
		bidRepository.resetBidTestTable();
	}
}
