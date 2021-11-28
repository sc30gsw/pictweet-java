package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.MTweet;
import com.example.demo.entity.TComment;
import com.example.demo.form.CommentForm;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.impl.SimpleLoginUser;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private TweetService tweetService;
	
	/**コメント投稿*/
	@Transactional
	public void setComment(CommentForm form, @AuthenticationPrincipal SimpleLoginUser loginUser, Integer tweetId) {
		TComment comment = new TComment();
		
		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);
		
		comment.setUserId(loginUser.getUser().getUserId());
		comment.setTweetId(tweet.getTweetId());
		comment.setCommentText(form.getCommentText());
		commentRepository.save(comment);
	}
}
