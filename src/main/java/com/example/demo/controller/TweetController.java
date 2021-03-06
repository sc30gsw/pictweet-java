package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.example.demo.entity.TComment;
import com.example.demo.form.CommentForm;
import com.example.demo.form.SearchListForm;
import com.example.demo.form.TweetForm;
import com.example.demo.service.CommentService;
import com.example.demo.service.TweetService;
import com.example.demo.service.impl.SimpleLoginUser;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/")
public class TweetController {

	@Autowired
	private TweetService tweetService;

	@Autowired
	private CommentService commentService;

	/**新規投稿画面に遷移*/
	@GetMapping("/new")
	public String getNew(@ModelAttribute("tweetForm") TweetForm form, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		return "tweet/new";
	}

	/**投稿詳細画面に遷移*/
	@GetMapping("/detail/{tweetId}")
	public String getTweet(@PathVariable("tweetId") Integer tweetId, @ModelAttribute("commentForm") CommentForm form,
			Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);
		model.addAttribute("tweet", tweet);

		//全コメント取得
		List<TComment> commentList = commentService.findAllComments();
		model.addAttribute("commentList", commentList);

		//ログインユーザーであればdetail.htmlに遷移
		if (loginUser != null) {
			//ログインユーザー情報(ユーザー名)取得
			String name = loginUser.getUser().getUsername();
			model.addAttribute("username", name);

			//ログインユーザーID取得
			Integer userId = loginUser.getUser().getUserId();
			model.addAttribute("userId", userId);

			return "tweet/detail";
		}

		//未ログインユーザーの遷移先
		return "tweet/_detail";
	}

	/**投稿編集画面に遷移*/
	@GetMapping("/edit/{tweetId}")
	public String getEdit(@PathVariable("tweetId") Integer tweetId, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);

		//ログインユーザーID取得
		Integer userId = loginUser.getUser().getUserId();
		//投稿者のユーザーID取得
		Integer tweetUserId = tweet.getUserId();

		//ログインユーザーと投稿者が同じなら編集画面に遷移
		if (userId == tweetUserId) {
			model.addAttribute("tweet", tweet);
			return "tweet/edit";
		}

		return "redirect:/index";
	}

	/**投稿更新機能*/
	@PostMapping("/edit/{tweetId}/update")
	public String putEdit(@PathVariable("tweetId") Integer tweetId, @ModelAttribute("tweetForm") TweetForm form,
			@Validated @ModelAttribute("tweet") MTweet tweet, BindingResult result, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		//MTweetのバリデーションチェック
		if (result.hasErrors()) {
			return "tweet/edit";
		}

		try {
			//更新機能
			tweetService.editTweet(form, loginUser, tweetId);
		} catch (Exception e) {
			log.error("投稿更新でエラー");
		}

		return "tweet/update";
	}

	/**投稿削除機能*/
	@PostMapping("/delete/{tweetId}/comfirm")
	public String postDeleteTweet(@PathVariable("tweetId") Integer tweetId, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);

		//ログインユーザーID取得
		Integer userId = loginUser.getUser().getUserId();
		//投稿者のユーザーID取得
		Integer tweetUserId = tweet.getUserId();

		//ログインユーザーと投稿者が同じなら削除を実行
		if (userId == tweetUserId) {
			tweetService.deleteTweetOne(tweetId);

			//削除完了画面に遷移
			return "tweet/delete";
		}

		return "redirect:/index";
	}

	/**投稿検索機能*/
	@PostMapping("/index")
	public String postTweetList(@ModelAttribute("searchForm") SearchListForm form, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		//ModelMapperの作成
		ModelMapper modelMapper = new ModelMapper();

		//formをMTweetに変換
		MTweet tweet = modelMapper.map(form, MTweet.class);

		//検索機能
		List<MTweet> searchTweetList = tweetService.getTweets(tweet);
		model.addAttribute("tweetList", searchTweetList);

		return "tweet/index";
	}

	/**データベース関連の例外処理*/
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		//空文字をセット
		model.addAttribute("error", "");

		//Modelにメッセージを登録
		model.addAttribute("message", "TweetControllerで例外が発生しました");

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
		model.addAttribute("message", "TweetControllerで例外が発生しました");

		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

}
