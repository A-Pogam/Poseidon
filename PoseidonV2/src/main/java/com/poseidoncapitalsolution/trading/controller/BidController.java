package com.poseidoncapitalsolution.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bid")
public class BidController {

	@Autowired
	private IBidService bidService;

	@GetMapping("/list")
	public String home(Model model) {
		model.addAttribute("bids", bidService.findAll());
		return "bid/list";
	}

	@GetMapping("/add")
	public String addBidForm(Bid bid) {
		return "bid/add";
	}

	@PostMapping("/validate")
	public String validate(@Valid Bid bid, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bid/add";
		}
		bidService.save(bid);
		return "redirect:/bid/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Bid bid = bidService.findById(id);
		if (bid == null) {
			throw new IllegalArgumentException("Invalid bid Id: " + id);
		}
		model.addAttribute("bid", bid);
		return "bid/update";
	}

	@PostMapping("/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid, BindingResult result) {
		if (result.hasErrors()) {
			return "bid/update";
		}
		bidService.update(id, bid);
		return "redirect:/bid/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id) {
		bidService.deleteById(id);
		return "redirect:/bid/list";
	}
}
