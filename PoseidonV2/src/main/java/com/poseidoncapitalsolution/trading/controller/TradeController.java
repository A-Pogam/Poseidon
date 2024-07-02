package com.poseidoncapitalsolution.trading.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.poseidoncapitalsolution.trading.model.Trade;
import com.poseidoncapitalsolution.trading.service.contracts.ITradeService;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/trade")
public class TradeController {

	@Autowired
	private ITradeService tradeService;

	@GetMapping("/list")
	public String showTradeList(Model model) {
		List<Trade> trades = tradeService.findAll();
		model.addAttribute("trades", trades);
		return "trade/list";
	}

	@GetMapping("/add")
	public String showAddTradeForm(Trade trade) {
		return "trade/add";
	}

	@PostMapping("/validate")
	public String addTrade(@Valid Trade trade, BindingResult result) {
		if (result.hasErrors()) {
			return "trade/add";
		}
		tradeService.save(trade);
		return "redirect:/trade/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateTradeForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeService.findById(id);
		if (trade == null) {
			throw new IllegalArgumentException("Invalid trade Id:" + id);
		}
		model.addAttribute("trade", trade);
		return "trade/update";
	}

	@PostMapping("/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result) {
		if (result.hasErrors()) {
			trade.setTradeId(id);
			return "trade/update";
		}
		tradeService.update(id, trade);
		return "redirect:/trade/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id) {
		tradeService.deleteById(id);
		return "redirect:/trade/list";
	}

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}
}
