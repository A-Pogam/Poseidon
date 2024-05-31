package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseidoncapitalsolution.trading.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}