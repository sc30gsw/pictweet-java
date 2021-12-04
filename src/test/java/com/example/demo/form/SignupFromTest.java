package com.example.demo.form;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.example.demo.repository.UserRepository;

@SpringBootTest
class SignupFromTest {
	
	@Autowired
	Validator validator;
	
	@Autowired
	UserRepository repository;
	
	private SignupForm testForm = new SignupForm();
	private BindingResult bindingResult = new BindException(testForm, "form");

	/**テストデータ*/
	private String testEmail = "testuser@user.com";
	private String testUsername = "test";
	private String testPassword = "test1111";
	
	/**
	 * 正常系
	 */
	@Test
	@DisplayName("emailとusername、passwordが存在すれば登録できる")
	void singupTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername(testUsername);
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertNull(bindingResult.getFieldError());
	}
	
	
	/**
	 * 異常系
	 */
	@Test
	@DisplayName("emailが空だと登録できない")
	void emailNullTest() {
		testForm.setEmail(null);
		testForm.setUsername(testUsername);
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("emailは必須入力です"));
	}
	
	@Test
	@DisplayName("emailがメールアドレス形式でないと登録できない")
	void emailFormatTest() {
		testForm.setEmail("email");
		testForm.setUsername(testUsername);
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("emailはメールアドレス形式で入力してください"));
	}
	
	@Test
	@DisplayName("emailがメールアドレス形式でないと登録できない")
	void emailUnusedTest() {
		//DBに登録されているテストデータを使用
		testForm.setEmail("test@co.com");
		testForm.setUsername(testUsername);
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("すでに登録済みのメールアドレスです"));
	}
	
	@Test
	@DisplayName("usernameが空だと登録できない")
	void usernameNullTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername(null);
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("usernameは必須入力です"));
	}
	
	@Test
	@DisplayName("usernameが7文字以上だと登録できない")
	void usernameMaxSizeTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername("testuser");
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("usernameは6以下を入力してください"));
	}
	
	@Test
	@DisplayName("usernameが3文字以下だと登録できない")
	void usernameMinSizeTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername("tes");
		testForm.setPassword(testPassword);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("usernameは4文字以上を入力してください"));
	}
	
	@Test
	@DisplayName("passwordが空だと登録できない")
	void passwordNullTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername(testUsername);
		testForm.setPassword(null);
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("passwordは必須入力です"));
	}
	
	@Test
	@DisplayName("usernameが7文字以下だと登録できない")
	void passwordMinSizeTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername(testUsername);
		testForm.setPassword("passwor");
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("usernameは8以上を入力してください"));
	}
	
	@Test
	@DisplayName("passwordが全角だと登録できない")
	void passwordPatternTest() {
		testForm.setEmail(testEmail);
		testForm.setUsername(testUsername);
		testForm.setPassword("パスワード");
		
		validator.validate(testForm, bindingResult);
		
		assertThat(bindingResult.getFieldError().toString().contains("passwordは半角英数字で入力してください"));
	}

}
