package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.Rating;
import com.poseidoncapitalsolution.trading.service.contracts.IRatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	private IRatingService ratingService;

	@GetMapping("/list")
	public String home(Model model) {
		model.addAttribute("ratings", ratingService.findAll());
		return "rating/list";
	}

	@GetMapping("/add")
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	@PostMapping("/validate")
	public String validate(@Valid Rating rating, BindingResult result) {
		if (result.hasErrors()) {
			return "rating/add";
		}
		ratingService.save(rating);
		return "redirect:/rating/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingService.findById(id);
		if (rating == null) {
			throw new IllegalArgumentException("Invalid rating Id:" + id);
		}
		model.addAttribute("rating", rating);
		return "rating/update";
	}

	@PostMapping("/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
							   Model model) {
		if (result.hasErrors()) {
			rating.setId(id);
			return "rating/update";
		}
		ratingService.update(id, rating);
		model.addAttribute("ratings", ratingService.findAll());
		return "redirect:/rating/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		ratingService.deleteById(id);
		model.addAttribute("ratings", ratingService.findAll());
		return "redirect:/rating/list";
	}
}
