package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.SignupForm;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String getTop() {
		return "top";
	}
	
	@GetMapping("/signup")
	public String getSignup(@ModelAttribute("user") SignupForm form) {
		return "user/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@Validated @ModelAttribute("user") SignupForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "user/signup";
		}
		
		userService.setUser(form);
		
		return "redirect:/";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "user/login";
	}
}
