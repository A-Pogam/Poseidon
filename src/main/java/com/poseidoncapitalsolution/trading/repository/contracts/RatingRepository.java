package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poseidoncapitalsolution.trading.constant.SqlQuery;
import com.poseidoncapitalsolution.trading.model.Rating;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	@Modifying
	@Transactional
	@Query(value = SqlQuery.truncateRatingTestTable, nativeQuery = true)
    void resetRatingTestTable();
}