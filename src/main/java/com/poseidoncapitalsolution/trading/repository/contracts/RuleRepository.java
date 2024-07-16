package com.poseidoncapitalsolution.trading.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poseidoncapitalsolution.trading.constant.SqlQuery;
import com.poseidoncapitalsolution.trading.model.Rule;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository

public interface RuleRepository extends JpaRepository<Rule, Integer> {

	@Modifying
	@Transactional
	@Query(value = SqlQuery.truncateRuleTestTable, nativeQuery = true)
    void resetRuleTestTable();
}