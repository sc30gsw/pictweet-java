package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.MTweet;
import com.example.demo.form.SignupForm;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.SimpleLoginUser;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@GetMapping("/")
	public String getTop(Model model) {
		
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
		return "top";
	}
	
	@GetMapping("/index")
	public String getIndex(Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);
		
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
        return "tweet/index";
	}
	
	@GetMapping("/signup")
	public String getSignup(@ModelAttribute("user") SignupForm form) {
		return "user/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@Validated @ModelAttribute("user") SignupForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/signup";
		}
		
		userService.setUser(form);
		
		String username = form.getUsername();
		model.addAttribute("username", username);
		
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
		return "tweet/index";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String postLogin(Model model) {
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
		return "redirect:/index";
	}
	
}
