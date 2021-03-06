package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.MUser;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	/**ユーザー登録機能*/
	@Transactional
	public void setUser(SignupForm form) {
		MUser user = new MUser();
		user.setUsername(form.getUsername());
		user.setEmail(form.getEmail());
		//パスワードの暗号化
		String rowPassword = form.getPassword();
		user.setPassword(encoder.encode(rowPassword));
		user.setRole("ROLE_GENERAL");
		userRepository.save(user);
	}

	/**メールアドレスでユーザー検索*/
	public Optional<MUser> findByEmail(String email) {		
		return userRepository.findByEmail(email);
	}
	
	/**ユーザー取得(1件)*/
	public MUser getUserOne(Integer userId) {
		Optional<MUser> option = userRepository.findById(userId);
		MUser user = option.orElse(null);
		
		return user;
	}
}
