package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Rule;
import com.poseidoncapitalsolution.trading.repository.contracts.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidoncapitalsolution.trading.service.contracts.IRuleService;

import java.util.List;
import java.util.Optional;

@Service
public class RuleService implements IRuleService {
    @Autowired
    private RuleRepository ruleRepository;

    @Override
    public List<Rule> findAll(){
        return ruleRepository.findAll();
    }

    @Override
    public Rule findById(Integer id) {
        Optional<Rule> optionalRule = ruleRepository.findById(id);
        return optionalRule.orElse(null);
    }

    @Override
    public Rule save(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public void update(Integer id, Rule rule) {
        rule.setId(id);
        ruleRepository.save(rule);
    }

    @Override
    public void deleteById(Integer id) {
        ruleRepository.deleteById(id);
    }


}