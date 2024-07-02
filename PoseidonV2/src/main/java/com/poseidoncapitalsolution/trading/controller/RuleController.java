package com.poseidoncapitalsolution.trading.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.poseidoncapitalsolution.trading.model.Rule;
import com.poseidoncapitalsolution.trading.service.contracts.IRuleService;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/rule")
public class RuleController {

	@Autowired
	private IRuleService ruleService;

	@GetMapping("/list")
	public String showRuleList(Model model) {
		List<Rule> rules = ruleService.findAll();
		model.addAttribute("rules", rules);
		return "rule/list";
	}

	@GetMapping("/add")
	public String showAddRuleForm(Model model) {
		model.addAttribute("rule", new Rule());
		return "rule/add";
	}

	@PostMapping("/add")
	public String addRule(@Valid Rule rule, BindingResult result) {
		if (result.hasErrors()) {
			return "rule/add";
		}
		ruleService.save(rule);
		return "redirect:/rule/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateRuleForm(@PathVariable("id") Integer id, Model model) {
		Rule rule = ruleService.findById(id);
		if (rule == null) {
			throw new IllegalArgumentException("Invalid rule Id:" + id);
		}
		model.addAttribute("rule", rule);
		return "rule/update";
	}

	@PostMapping("/update/{id}")
	public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule, BindingResult result) {
		if (result.hasErrors()) {
			rule.setId(id);
			return "rule/update";
		}
		ruleService.update(id, rule);
		return "redirect:/rule/list";
	}

	@PostMapping("/delete/{id}")
	public String deleteRule(@PathVariable("id") Integer id) {
		ruleService.deleteById(id);
		return "redirect:/rule/list";
	}

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}
}
