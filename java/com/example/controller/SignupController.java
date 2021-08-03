package com.example.controller;

import java.util.Map;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindingResult;

import org.springframework.validation.annotation.Validated;


@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {
	@Autowired
	private UserApplicationService userApplicationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/signup")
	public String getSignup(Model model,Locale locale,@ModelAttribute SignupForm form) {
		
		Map<String ,Integer> genderMap = userApplicationService.getGenderMap();
		model.addAttribute("genderMap",genderMap);
		return "user/signup";
	}
	
	@PostMapping("/signup")
	public String posSignup(Model model,Locale locale,@ModelAttribute @Validated(GroupOrder.class) SignupForm form,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			//NG : ユーザー登録画面に戻ります。
			return getSignup(model,locale,form);
		}
		
		log.info(form.toString());
		
		//form をMUserクラスに変換
		MUser user = modelMapper.map(form,MUser.class);
		
		//ユーザー登録
		userService.signup(user);
		
		return "redirect:/login";		
		
	}
	
}
