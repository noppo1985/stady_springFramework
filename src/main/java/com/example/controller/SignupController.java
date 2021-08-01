package com.example.controller;

import java.util.Map;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import com.example.application.service.UserApplicationService;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {
	@Autowired
	private UserApplicationService userApplicationService;
	
	@GetMapping("/signup")
	public String getSignup(Model model,Locale locale,@ModelAttribute SignupForm form) {
		
		Map<String ,Integer> genderMap = userApplicationService.getGenderMap();
		model.addAttribute("genderMap",genderMap);
		return "user/signup";
	}
	@PostMapping("/signup")
	public String posSignup(Model model,Locale locale,@ModelAttribute SignupForm form,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			//NG : ユーザー登録画面に戻ります。
			return getSignup(model,locale,form);
		}
		

		log.info(form.toString()); 
		return "redirect:/login";		
		
	}
	
}
