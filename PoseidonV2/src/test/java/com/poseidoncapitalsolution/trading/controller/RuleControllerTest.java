package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.Rule;
import com.poseidoncapitalsolution.trading.service.contracts.IRuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RuleController.class)
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRuleService ruleService;

    private Rule anyRule = new Rule(1, "Rule1");
    private List<Rule> rules = new ArrayList<>(Arrays.asList(anyRule, anyRule, anyRule));

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void showRuleList_returnOk() throws Exception {
        when(ruleService.findAll()).thenReturn(rules);

        mockMvc.perform(get("/rule/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/list"))
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void showAddRuleForm_returnOk() throws Exception {
        mockMvc.perform(get("/rule/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/add"))
                .andExpect(model().attributeExists("rule"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void addRule_successAndRedirectToListPage() throws Exception {

        mockMvc.perform(post("/rule/add")
                        .flashAttr("rule", anyRule)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rule/list"));

        verify(ruleService, times(1)).save(any(Rule.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void addRule_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/rule/add")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/add"))
                .andExpect(model().attributeExists("rule"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void showUpdateRuleForm_returnOk() throws Exception {
        when(ruleService.findById(anyInt())).thenReturn(anyRule);

        mockMvc.perform(get("/rule/update/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/update"))
                .andExpect(model().attributeExists("rule"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void updateRule_successAndRedirectToListPage() throws Exception {
        when(ruleService.findAll()).thenReturn(rules);

        mockMvc.perform(post("/rule/update/{id}", "1")
                        .flashAttr("rule", anyRule)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/rule/list"));

        verify(ruleService, times(1)).update(anyInt(), any(Rule.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void updateRule_failAndReturnOk() throws Exception {
        mockMvc.perform(post("/rule/update/{id}", "1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/update"))
                .andExpect(model().attributeExists("rule"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void deleteRule_successAndRedirectToListPage() throws Exception {
        mockMvc.perform(get("/rule/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rule/list"));

        verify(ruleService, times(1)).deleteById(anyInt());
    }
}
