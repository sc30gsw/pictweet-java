package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MUser;
import com.example.demo.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		MUser loginUser =  service.findByEmail(email);
		
		//ユーザーが存在しない場合
		if(loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		//権限リスト作成
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);
		
		//UserDetails作成
		UserDetails userDetail = (UserDetails) new User(loginUser.getEmail(), loginUser.getPassword(), authorities);
		
		return userDetail;
	}
}
