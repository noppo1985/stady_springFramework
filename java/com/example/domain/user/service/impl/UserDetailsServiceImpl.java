package com.example.domain.user.service.impl;

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

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService service;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		//ユーザー情報取得
		MUser loginUser = service.getLoginUser(username);
		
		if(loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		//権限List作成
		GrantedAuthority authrity = new SimpleGrantedAuthority(loginUser.getRole());		
		List<GrantedAuthority> authrities = new ArrayList<>();
		authrities.add(authrity);
		
		//UserDetails生成
		UserDetails userDetails = (UserDetails) new User(loginUser.getUserId(),loginUser.getPassword(),authrities);
		
		return userDetails;
	}

}
