package com.example.demo.controller;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.TweetForm;
import com.example.demo.service.TweetService;
import com.example.demo.service.impl.SimpleLoginUser;

/**
 * 非同期通信用ツイートコントローラークラス
 */
@RestController
@RequestMapping("/")
public class TweetRestController {
	
	@Autowired
	private TweetService tweetService;
	
	@Autowired
	Validator validator;

	/**
	 * 非同期で新規投稿を行う処理
	 * 
	 * @param form
	 * @param loginUser
	 * @return
	 */
	@PostMapping("/comfirm")
	public int postNew(TweetForm form, @AuthenticationPrincipal SimpleLoginUser loginUser) {

		validator.validate(form);
		//投稿機能
		tweetService.setTweet(form, loginUser);

		return 0;
	}
}
