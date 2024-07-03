package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.Bid;
import java.util.List;

/**
 * IBidService is an interface that defines the contract for the service layer managing Bid entities.
 * <p>
 * It provides methods for common CRUD operations (Create, Read, Update, Delete) and an additional method
 * for resetting the bid table, particularly useful for testing purposes.
 * </p>
 *
 * @author Alice Pogam
 * @version 1.0
 */
public interface IBidService {

	/**
	 * Retrieves all Bid entities from the database.
	 *
	 * @return a list of all bids.
	 */
	List<Bid> findAll();

	/**
	 * Retrieves a specific Bid by its unique identifier.
	 *
	 * @param id the unique identifier of the bid.
	 * @return the Bid entity if found, otherwise null.
	 */
	Bid findById(Integer id);

	/**
	 * Saves a new Bid entity to the database or updates an existing one.
	 * <p>
	 * If the bid has an ID, it updates the existing record; if not, it creates a new record.
	 * </p>
	 *
	 * @param bid the Bid entity to be saved or updated.
	 */
	void save(Bid bid);

	/**
	 * Updates an existing Bid entity identified by its ID.
	 *
	 * @param id the unique identifier of the bid to be updated.
	 * @param bid the Bid entity with updated information.
	 */
	void update(Integer id, Bid bid);

	/**
	 * Deletes a Bid entity by its unique identifier.
	 *
	 * @param id the unique identifier of the bid to be deleted.
	 */
	void deleteById(Integer id);

	/**
	 * Resets the Bid table to its initial state.
	 * <p>
	 * This method is typically used to clear or reset the table for testing purposes.
	 * </p>
	 */
	void resetBidTestTable();
}
