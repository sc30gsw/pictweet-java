package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.MTweet;
import com.example.demo.form.TweetForm;
import com.example.demo.repository.TweetRepository;
import com.example.demo.service.impl.SimpleLoginUser;

@Service
public class TweetService {

	@Autowired
	private TweetRepository tweetRepository;
	
	/**投稿機能*/
	@Transactional
	public void setTweet(TweetForm form, @AuthenticationPrincipal SimpleLoginUser loginUser) {
		MTweet tweet = new MTweet();
		tweet.setUserId(loginUser.getUser().getUserId());
		tweet.setText(form.getText());
		tweet.setImageUrl(form.getImageUrl());
		tweetRepository.save(tweet);
	}
	
	/**投稿全取得*/
	public List<MTweet> findAllTweets(){
		return tweetRepository.findAll();
	}
	
	/**投稿取得(1件)*/
	public MTweet getTweetOne(Integer tweetId) {
		Optional<MTweet> option = tweetRepository.findById(tweetId);
		MTweet tweet = option.orElse(null);
		
		return tweet;
	}
	
	/**投稿更新機能*/
	@Transactional
	public void editTweet(TweetForm form, @AuthenticationPrincipal SimpleLoginUser loginUser, Integer tweetId) {
		Optional<MTweet> option = tweetRepository.findById(tweetId);
		MTweet tweet = option.orElse(null);
		tweet.setUserId(loginUser.getUser().getUserId());
		tweet.setText(form.getText());
		tweet.setImageUrl(form.getImageUrl());
		tweetRepository.save(tweet);
	}
}
