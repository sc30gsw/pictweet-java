package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MTweet;
import com.example.demo.form.TweetForm;
import com.example.demo.repository.TweetRepository;
import com.example.demo.service.impl.SimpleLoginUser;

@Service
public class TweetService {

	@Autowired
	private TweetRepository tweetRepository;
	
	public void setTweet(TweetForm form, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		MTweet tweet = new MTweet();
		tweet.setUserId(loginUser.getUser().getUserId());
		tweet.setText(form.getText());
		tweet.setImageUrl(form.getImageUrl());
		tweetRepository.save(tweet);
	}
}
