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
	public String getNew(@ModelAttribute("tweetForm") TweetForm form, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		return "tweet/new";
	}

	/**投稿機能*/
	@PostMapping("/comfirm")
	public String postNew(@Validated @ModelAttribute("tweetForm") TweetForm form, BindingResult result, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//ログインユーザー情報(ユーザー名)取得
		String name = loginUser.getUser().getUsername();
		model.addAttribute("username", name);

		//formのバリデーションチェック
		if (result.hasErrors()) {
			return "tweet/new";
		}

		//投稿機能
		tweetService.setTweet(form, loginUser);

		return "tweet/comfirm";
	}

	/**投稿詳細画面に遷移*/
	@GetMapping("/detail/{tweetId}")
	public String getTweet(@PathVariable("tweetId") Integer tweetId, Model model,
			@AuthenticationPrincipal SimpleLoginUser loginUser) {
		//投稿1件取得
		MTweet tweet = tweetService.getTweetOne(tweetId);
		model.addAttribute("tweet", tweet);

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

		//更新機能
		tweetService.editTweet(form, loginUser, tweetId);

		return "tweet/update";
	}
	
	/**投稿削除機能*/
	@PostMapping("/delete/{tweetId}/comfirm")
	public String postDeleteTweet(@PathVariable("tweetId") Integer tweetId, Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
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

}
