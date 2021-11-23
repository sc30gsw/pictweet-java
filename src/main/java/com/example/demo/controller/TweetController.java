package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.MTweet;
import com.example.demo.form.TweetForm;
import com.example.demo.service.TweetService;
import com.example.demo.service.impl.SimpleLoginUser;

@Controller
@RequestMapping("/")
public class TweetController {

	@Autowired
	private TweetService tweetService;

	/**新規投稿画面に遷移*/
	@GetMapping("/new")
	public String getNew(@ModelAttribute("tweetForm") TweetForm form, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		return "tweet/new";
	}

	/**投稿機能*/
	@PostMapping("/comfirm")
	public String postNew(@Validated @ModelAttribute("tweetForm") TweetForm form, BindingResult result, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		if (result.hasErrors()) {
			return "tweet/new";
		}

		//投稿機能
		tweetService.setTweet(form, loginUser);

		return "tweet/comfirm";
	}
	
	/**投稿詳細画面に遷移*/
	@GetMapping("/detail/{tweetId}")
	public String getTweet(@PathVariable("tweetId") Integer tweetId, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);
		model.addAttribute("tweet", tweet);
		
		//ログインユーザーであればdetail.htmlに遷移
		if(loginUser != null) {
			//ログインユーザー情報(ユーザー名)取得
			String name = loginUser.getUser().getUsername();
			model.addAttribute("username", name);
			
			return "tweet/detail";
		}
		
		//未ログインユーザーの遷移先
		return "tweet/_detail";
	}
}
