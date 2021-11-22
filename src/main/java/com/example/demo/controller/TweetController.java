package com.example.demo.controller;

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

import com.example.demo.form.TweetForm;
import com.example.demo.service.TweetService;
import com.example.demo.service.impl.SimpleLoginUser;

@Controller
@RequestMapping("/")
public class TweetController {

	@Autowired
	private TweetService tweetService;

	@GetMapping("/new")
	public String getNew(@ModelAttribute("tweetForm") TweetForm form, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		return "tweet/new";
	}

	@PostMapping("/comfirm")
	public String postNew(@Validated @ModelAttribute("tweetForm") TweetForm form, BindingResult result, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		if (result.hasErrors()) {
			return "tweet/new";
		}

		tweetService.setTweet(form, loginUser);

		return "tweet/comfirm";
	}
}
