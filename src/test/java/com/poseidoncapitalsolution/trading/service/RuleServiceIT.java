package com.poseidoncapitalsolution.trading.service;

import com.poseidoncapitalsolution.trading.model.Rule;
import com.poseidoncapitalsolution.trading.repository.contracts.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleServiceIT {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleService ruleService;

    private Rule rule;

    @BeforeEach
    public void setUp() {
        rule = new Rule();
        rule.setId(1);
        rule.setName("Test Rule");
        rule.setDescription("This is a test rule");
        rule.setJson("testJson");
        rule.setTemplate("testTemplate");
        rule.setSqlPart("WHERE TEST = 1");
    }

    @Test
    public void testFindAll() {
        List<Rule> rules = Arrays.asList(rule, new Rule());
        when(ruleRepository.findAll()).thenReturn(rules);

        List<Rule> result = ruleService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ruleRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));

        Rule result = ruleService.findById(1);

        assertNotNull(result);
        assertEquals(rule.getId(), result.getId());
        verify(ruleRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testFindById_NotFound() {
        when(ruleRepository.findById(anyInt())).thenReturn(Optional.empty());

        Rule result = ruleService.findById(1);

        assertNull(result);
        verify(ruleRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testSave() {
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);

        Rule result = ruleService.save(rule);

        assertNotNull(result);
        assertEquals(rule.getId(), result.getId());
        verify(ruleRepository, times(1)).save(any(Rule.class));
    }

    @Test
    public void testUpdate() {
        ruleService.update(1, rule);

        verify(ruleRepository, times(1)).save(rule);
        assertEquals(1, rule.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(ruleRepository).deleteById(anyInt());

        ruleService.deleteById(1);

        verify(ruleRepository, times(1)).deleteById(anyInt());
    }
}
