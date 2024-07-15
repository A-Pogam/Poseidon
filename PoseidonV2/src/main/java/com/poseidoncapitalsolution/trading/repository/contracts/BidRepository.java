package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poseidoncapitalsolution.trading.constant.SqlQuery;
import com.poseidoncapitalsolution.trading.model.Bid;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Bid entities.
 * Extends {@link JpaRepository} to provide CRUD operations for Bid entities.
 * Includes a custom method to reset the bid test table.
 */


@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
	/**
	 * Custom method to truncate the bid test table.
	 * This method is annotated with {@link Modifying} and {@link Transactional}
	 * to indicate that it performs a modifying operation and should be executed within a transaction.
	 * The SQL query for truncating the table is defined in {@link SqlQuery#truncateBidTestTable}.
	 */
	@Modifying
	@Transactional
	@Query(value = SqlQuery.truncateBidTestTable, nativeQuery = true)
	void resetBidTestTable();
}