package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MUser;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void setUser(SignupForm form) {
		MUser user = new MUser();
		user.setUsername(form.getUsername());
		user.setEmail(form.getEmail());
		user.setPassword(form.getPassword());
		user.setRole("ROLE_GENERAL");
		userRepository.save(user);
	}

	public MUser findByEmail(String email) {		
		return userRepository.findByEmail(email);
	}

}
