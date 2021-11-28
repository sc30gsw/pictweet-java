package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.MTweet;
import com.example.demo.form.CommentForm;
import com.example.demo.service.CommentService;
import com.example.demo.service.TweetService;
import com.example.demo.service.impl.SimpleLoginUser;

@Controller
@RequestMapping("/")
public class CommentController {

	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/detail/{tweetId}/comment")
	public String postComment(@PathVariable("tweetId") Integer tweetId,
			@Validated @ModelAttribute("commentForm") CommentForm form, BindingResult result, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);
		
		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);
				
		//formのバリデーションチェック
		if (result.hasErrors()) {
			model.addAttribute("tweet", tweet);
			return "tweet/detail";
		}
		
		model.addAttribute("tweet", tweet);
		
		//投稿機能
		commentService.setComment(form, loginUser, tweetId);

		return "tweet/detail";
	}
}
