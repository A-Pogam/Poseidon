package com.poseidoncapitalsolution.trading.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.poseidoncapitalsolution.trading.model.Bid;
import com.poseidoncapitalsolution.trading.service.contracts.IBidService;

import jakarta.validation.Valid;

/**
 * Controller handling requests related to the {@link Bid} entity.
 * <p>
 * This controller provides endpoints for listing, adding, updating, and deleting bids.
 * It uses {@link IBidService} to execute the business logic and interacts with the view layer
 * to render appropriate responses.
 * </p>
 *
 * @author Alice Pogam
 * @version 1.0
 */
@Controller
public class BidController {

	private final Logger logger = LogManager.getLogger(HomeController.class);

	@Autowired
	private IBidService bidService;

	/**
	 * Retrieves a list of all bids and forwards to the bids list view.
	 *
	 * @param model the model to add attributes for the view
	 * @return the view name for listing bids
	 */
	@GetMapping("/bid/list")
	public String getList(Model model) {
		model.addAttribute("bids", bidService.findAll());
		return "bid/list";
	}

	/**
	 * Retrieves the form for adding a new bid.
	 *
	 * @param bid the Bid object to be populated in the form
	 * @return the view name for the add bid form
	 */
	@GetMapping("/bid/add")
	public String getAddForm(Bid bid) {
		return "bid/add";
	}

	/**
	 * Validates and saves a new bid.
	 *
	 * @param bid    the Bid object to be validated and saved
	 * @param result the binding result object to hold validation errors
	 * @return the view name for adding a bid or redirects to the bid list view if successful
	 */
	@PostMapping("/bid/validate")
	public String addBid(@Valid Bid bid, BindingResult result) {
		if (result.hasErrors()) {
			return "bid/add";
		}
		bidService.save(bid);
		return "redirect:/bid/list";
	}

	/**
	 * Retrieves the form for updating an existing bid by its ID.
	 *
	 * @param id    the ID of the bid to be updated
	 * @param model the model to add attributes for the view
	 * @return the view name for the update bid form or throws an exception if the bid ID is invalid
	 */
	@GetMapping("/bid/update/{id}")
	public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
		Bid bid = bidService.findById(id);
		if (bid == null) {
			throw new IllegalArgumentException("Invalid bid Id: " + id);
		}
		model.addAttribute("bid", bid);
		return "bid/update";
	}

	/**
	 * Validates and updates an existing bid by its ID.
	 *
	 * @param id     the ID of the bid to be updated
	 * @param bid    the Bid object with updated information
	 * @param result the binding result object to hold validation errors
	 * @return the view name for updating a bid or redirects to the bid list view if successful
	 */
	@PostMapping("/bid/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid, BindingResult result) {
		if (result.hasErrors()) {
			return "bid/update";
		}
		bidService.update(id, bid);
		return "redirect:/bid/list";
	}

	/**
	 * Deletes an existing bid by its ID.
	 *
	 * @param id the ID of the bid to be deleted
	 * @return redirects to the bid list view after deleting the bid
	 */
	@GetMapping("/bid/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id) {
		bidService.deleteById(id);
		return "redirect:/bid/list";
	}

	/**
	 * Retrieves the remote user from the HTTP servlet request and adds it as a model attribute.
	 *
	 * @param httpServletRequest the HTTP servlet request to retrieve the remote user
	 * @return the remote user object
	 */
	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}
}
