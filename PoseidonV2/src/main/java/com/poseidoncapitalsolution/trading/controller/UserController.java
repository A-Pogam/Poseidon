package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.service.contracts.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

	private final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private IUserService iUserService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/user/list")
	public String getUserList(Model model) {
		model.addAttribute("users", iUserService.getAllUsers());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User user) {
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String postUser(@Valid User user, BindingResult result) {
			if (result.hasErrors()) {
				return "user/add";
			}
			iUserService.saveUser(user);
			return "redirect:/user/list";
		}


		@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = iUserService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));

		user.setPassword("");

		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String putUserForUserUpdate(@PathVariable("id") Integer id, @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/update";
		}
		iUserService.saveUser(user);
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteById(@PathVariable("id") Integer id) {
		iUserService.deleteById(id);
		return "redirect:/user/list";
	}

	@ModelAttribute("remoteUser")
	public Object remoteUser(final HttpServletRequest httpServletRequest) {
		return httpServletRequest.getRemoteUser();
	}
}
