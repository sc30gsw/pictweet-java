package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.MTweet;
import com.example.demo.entity.MUser;
import com.example.demo.form.SearchListForm;
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
	public String getTop(@ModelAttribute("searchForm") SearchListForm form, Model model) {
		
		//全投稿取得
		List<MTweet> tweetList = tweetService.findAllTweets();
		model.addAttribute("tweetList", tweetList);
		
		return "top";
	}
	
	/**ユーザー認証後のトップページに遷移*/
	@GetMapping("/index")
	public String getIndex(@ModelAttribute("searchForm") SearchListForm form, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
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
	public String postSignup(@ModelAttribute("searchForm") SearchListForm searchForm, @Validated @ModelAttribute("user") SignupForm form, BindingResult result, Model model) {
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
		log.info("ログイン");
		return "redirect:/index";
	}
	
	/**ユーザー詳細画面に遷移*/
	@GetMapping("/detail/user/{userId}")
	public String getTweet(@PathVariable("userId") Integer userId, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザーであればdetail.htmlに遷移
		if (loginUser != null) {
			//ログインユーザー情報(ユーザー名)取得
			String name = loginUser.getUser().getUsername();
			model.addAttribute("username", name);
			
			//ユーザー1件取得
			MUser user= userService.getUserOne(userId);
			model.addAttribute("user", user);
			
			//投稿者の全投稿取得
			List<MTweet> tweetList = user.getTweetList();
			model.addAttribute("tweetList", tweetList);
			
			return "user/detail";
		}

		//ユーザー1件取得
		MUser user= userService.getUserOne(userId);
		model.addAttribute("user", user);
		
		//投稿者の全投稿取得
		List<MTweet> tweetList = user.getTweetList();
		model.addAttribute("tweetList", tweetList);
		
		return "user/_detail";
	}
	
	/**データベース関連の例外処理*/
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		
		//空文字をセット
		model.addAttribute("error", "");
		
		//Modelにメッセージを登録
		model.addAttribute("message", "UserControllerで例外が発生しました");
		
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
	/**その他の例外処理*/
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		
		//空文字をセット
		model.addAttribute("error", "");
		
		//Modelにメッセージを登録
		model.addAttribute("message", "UserControllerで例外が発生しました");
		
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
	
}
