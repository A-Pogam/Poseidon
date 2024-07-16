package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Rule;
import com.poseidoncapitalsolution.trading.service.contracts.IRuleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "file:src/main/resources/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RuleServiceIT {

    @Autowired
    private IRuleService ruleService;

    private Rule rule;

    @BeforeAll
    public void setUp() {
        rule = new Rule();
        rule.setName("Test Rule");
        rule.setDescription("This is a test rule");
        rule.setJson("testJson");
        rule.setTemplate("testTemplate");
        rule.setSqlPart("WHERE TEST = 1");

        ruleService.save(rule);
    }

    @Test
    public void testFindAll() {
        List<Rule> rules = ruleService.findAll();

        assertNotNull(rules);
        assertEquals(1, rules.size());
    }

    @Test
    public void testFindById() {
        Rule foundRule = ruleService.findById(rule.getId());

        assertNotNull(foundRule);
        assertEquals(rule.getId(), foundRule.getId());
    }

    @Test
    public void testFindById_NotFound() {
        Rule foundRule = ruleService.findById(-1);

        assertNull(foundRule);
    }

    @Test
    public void testSave() {
        Rule newRule = new Rule();
        newRule.setName("New Rule");
        newRule.setDescription("This is a new rule");

        Rule savedRule = ruleService.save(newRule);

        assertNotNull(savedRule);
        assertNotNull(savedRule.getId());
        assertEquals(newRule.getName(), savedRule.getName());
        assertEquals(newRule.getDescription(), savedRule.getDescription());

        ruleService.deleteById(savedRule.getId());
    }

    @Test
    public void testUpdate() {
        rule.setName("Updated Rule");
        rule.setDescription("This is an updated rule");

        ruleService.update(rule.getId(), rule);

        Rule updatedRule = ruleService.findById(rule.getId());

        assertNotNull(updatedRule);
        assertEquals(rule.getName(), updatedRule.getName());
        assertEquals(rule.getDescription(), updatedRule.getDescription());
    }

    @Test
    public void testDeleteById() {
        ruleService.deleteById(rule.getId());

        Rule deletedRule = ruleService.findById(rule.getId());

        assertNull(deletedRule);
    }

    @Test
    public void testSaveRule_WithMissingName() {
        Rule rule = new Rule();
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlPart("Sql Part");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ruleService.save(rule));
        assertEquals("Name is mandatory", exception.getMessage());
    }

    @Test
    public void testSaveRule_WithMissingDescription() {
        Rule rule = new Rule();
        rule.setName("Test Rule");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlPart("Sql Part");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ruleService.save(rule));
        assertEquals("Description is mandatory", exception.getMessage());
    }

    @Test
    public void testSaveRule_WithMissingJson() {
        Rule rule = new Rule();
        rule.setName("Test Rule");
        rule.setDescription("Description");
        rule.setTemplate("Template");
        rule.setSqlPart("Sql Part");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ruleService.save(rule));
        assertEquals("Json is mandatory", exception.getMessage());
    }

    @Test
    public void testSaveRule_WithMissingTemplate() {
        Rule rule = new Rule();
        rule.setName("Test Rule");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setSqlPart("Sql Part");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ruleService.save(rule));
        assertEquals("Template is mandatory", exception.getMessage());
    }

    @Test
    public void testSaveRule_WithMissingSqlPart() {
        Rule rule = new Rule();
        rule.setName("Test Rule");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ruleService.save(rule));
        assertEquals("Sql Part is mandatory", exception.getMessage());
    }
}
