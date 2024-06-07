package com.poseidoncapitalsolution.trading.controller;

import com.poseidoncapitalsolution.trading.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.poseidoncapitalsolution.trading.model.User;
import com.poseidoncapitalsolution.trading.repository.contracts.UserRepository;

import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserController {

	private final Logger logger = LogManager.getLogger(HomeController.class);


	@Autowired
	private UserRepository iUserRepository;
	@Autowired
	private UserService userService;


	@RequestMapping("/user/list")
	public String getUserList(Model model) {
		model.addAttribute("users", iUserRepository.findAll());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = iUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");

		model.addAttribute("user", user);
		return "user/update";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = iUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		iUserRepository.delete(user);

		model.addAttribute("users", iUserRepository.findAll());
		return "redirect:/user/list";
	}

	@PutMapping("/user/update/{id}")
	public String putUserForUserUpdate(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/update";
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setId(id);
		iUserRepository.save(user);

		model.addAttribute("users", iUserRepository.findAll());
		return "redirect:/user/list";
	}

	@PostMapping("/user/validate")
	public String postUser(@Valid @RequestBody User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user data");
		}

		try {
			userService.saveUser(user);
			model.addAttribute("users", userService.getAllUsers());
			return "redirect:/user/list";
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save user", e);
		}
	}




	}
