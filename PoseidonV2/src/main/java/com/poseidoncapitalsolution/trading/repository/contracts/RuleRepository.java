package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseidoncapitalsolution.trading.model.Rule;

public interface RuleRepository extends JpaRepository<Rule, Integer> {

}