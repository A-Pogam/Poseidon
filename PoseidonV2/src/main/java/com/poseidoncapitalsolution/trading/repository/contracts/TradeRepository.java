package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseidoncapitalsolution.trading.model.Trade;
import org.springframework.stereotype.Repository;

@Repository

public interface TradeRepository extends JpaRepository<Trade, Integer> {

}