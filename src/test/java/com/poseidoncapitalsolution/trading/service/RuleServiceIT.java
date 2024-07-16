package com.poseidoncapitalsolution.trading.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.poseidoncapitalsolution.trading.model.Rule;
import com.poseidoncapitalsolution.trading.service.contracts.IRuleService;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleServiceIT {

	@Autowired
	private IRuleService iRuleService;
	
	@BeforeAll
	public void fillRuleTable() {
		for (int i = 1; i <= 3; i++) {
			iRuleService.save(new Rule(null, "Name" + i, "Description" + i, "Json" + i, "Template" + i, "SQL Part" + i));
		}
	}
	
	@AfterAll
	public void resetRuleTable() {
		iRuleService.resetRuleTestTable();
	}
	
	@Test
	public void getRules_returnRules() {
		List<Rule> rules = iRuleService.findAll();
		
		assertThat(rules).isNotEmpty();
		assertThat(rules.getFirst().getId()).isEqualTo(1);
		assertThat(rules.getFirst().getName()).isEqualTo("Name1");
		assertThat(rules.getFirst().getDescription()).isEqualTo("Description1");
		assertThat(rules.getFirst().getJson()).isEqualTo("Json1");
		assertThat(rules.getFirst().getTemplate()).isEqualTo("Template1");
		assertThat(rules.getFirst().getSqlPart()).isEqualTo("SQL Part1");
	}

	@Test
	public void getRuleById_returnRule() {
		Rule rule = iRuleService.findById(1);
		
		assertThat(rule.getId()).isEqualTo(1);
		assertThat(rule.getName()).isEqualTo("Name1");
		assertThat(rule.getDescription()).isEqualTo("Description1");
		assertThat(rule.getJson()).isEqualTo("Json1");
		assertThat(rule.getTemplate()).isEqualTo("Template1");
		assertThat(rule.getSqlPart()).isEqualTo("SQL Part1");
	}
	
	@Test
	public void getRuleById_returnNull() {
		Rule rule = iRuleService.findById(0);
		
		assertThat(rule).isEqualTo(null);
	}

	@Test
	public void saveRule_returnRule() {
		Rule newRule = new Rule(null, "NewName", "NewDescription", "NewJson", "NewTemplate", "NewSQL Part");
		
		Rule ruleAdded = iRuleService.save(newRule);
		
		assertThat(ruleAdded.getName()).isEqualTo(newRule.getName());
		assertThat(ruleAdded.getDescription()).isEqualTo(newRule.getDescription());
		assertThat(ruleAdded.getJson()).isEqualTo(newRule.getJson());
		assertThat(ruleAdded.getTemplate()).isEqualTo(newRule.getTemplate());
		assertThat(ruleAdded.getSqlPart()).isEqualTo(newRule.getSqlPart());
	}
	
	@Test
	public void updateRule_returnUpdatedBid() {
	    Integer existingRuleId = 3;
	    Rule updatedRule = new Rule(null, "UpdatedName", "UpdatedDescription", "UpdatedJson", "UpdatedTemplate", "UpdatedSQL Part");
	
	    iRuleService.update(existingRuleId, updatedRule);
	
	    Rule rule = iRuleService.findById(existingRuleId);
	    assertThat(rule).isNotNull();
	    assertThat(rule.getName()).isEqualTo(updatedRule.getName());
	    assertThat(rule.getDescription()).isEqualTo(updatedRule.getDescription());
	    assertThat(rule.getJson()).isEqualTo(updatedRule.getJson());
	    assertThat(rule.getTemplate()).isEqualTo(updatedRule.getTemplate());
	    assertThat(rule.getSqlPart()).isEqualTo(updatedRule.getSqlPart());
	}
	
	@Test
	public void deleteRule_deleteRule() {
		Rule ruleToDelete = iRuleService.findById(2);
		
		iRuleService.deleteById(2);
		
		assertThat(!iRuleService.findAll().contains(ruleToDelete));
	}
}
