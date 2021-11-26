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

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	/**トップページに遷移*/
	@GetMapping("/")
	public String getTop(Model model) {
		
		//全投稿取得
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
		return "top";
	}
	
	/**ユーザー認証後のトップページに遷移*/
	@GetMapping("/index")
	public String getIndex(Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);
		
		//ログインユーザーID取得
		Integer userId = loginUser.getUser().getUserId();
		model.addAttribute("userId", userId);
		
		//全投稿取得
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
        return "tweet/index";
	}
	
	/**ユーザー登録画面に遷移*/
	@GetMapping("/signup")
	public String getSignup(@ModelAttribute("user") SignupForm form) {
		return "user/signup";
	}
	
	/**ユーザー登録機能*/
	@PostMapping("/signup")
	public String postSignup(@Validated @ModelAttribute("user") SignupForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/signup";
		}
		
		log.info(form.toString());
		
		//ユーザー登録
		userService.setUser(form);
		
		//フォームに入力されたユーザー名取得
		String username = form.getUsername();
		model.addAttribute("username", username);
		
		//全投稿取得
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
		return "tweet/index";
	}

	/**ログイン画面に遷移*/
	@GetMapping("/login")
	public String getLogin() {
		return "user/login";
	}
	
	/**ログイン機能*/
	@PostMapping("/login")
	public String postLogin(Model model) {
		log.info("ログアウト");
		return "redirect:/index";
	}
	
}
