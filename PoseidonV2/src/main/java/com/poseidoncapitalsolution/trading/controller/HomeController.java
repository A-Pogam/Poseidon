package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.service.contracts.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private final Logger logger = LogManager.getLogger(HomeController.class);

	@Autowired
	private IUserService userService;

	@GetMapping("/home")
	public String home(Model model) {
		logger.info("Received request to access home page.");

		String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		model.addAttribute("user", user);

		return "home";
	}

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}

}