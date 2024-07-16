package com.poseidoncapitalsolution.trading.service.contracts;

import com.poseidoncapitalsolution.trading.model.Rule;

import java.util.List;

public interface IRuleService {
	List<Rule> findAll();

	Rule findById(Integer id);;

	Rule save(Rule rule);

	void update(Integer id, Rule rule);

	void deleteById(Integer id);

	void resetRuleTestTable();
}