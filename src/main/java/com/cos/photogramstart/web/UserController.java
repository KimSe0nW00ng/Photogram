package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	@GetMapping("/user/{pageUserid}") //해당 페이지의 아이디              (아래) 현재 로그인 한 사용자의 아이디
	public String profile(@PathVariable int pageUserid, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.회원프로필(pageUserid, principalDetails.getUser().getId());//pageUserid(페이지의 아이디), principalDetails.getUser().getId()(현재 로그인 한 사람의 아이디)
		model.addAttribute("dto", dto);
		return "user/profile";
	}
	
	
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 세션의 정보를 담고 있다,,,
		//1. 추천
		//System.out.println("세션 정보"+ principalDetails.getUser());
		//2.다른방법 (비추천)
		//Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		//PrincipalDetails mPrincipaldetails = (PrincipalDetails)auth.getPrincipal();
		//System.out.println("직접 찾은 세션 정보:"+mPrincipaldetails.getUser());
		// 무한루프로 인해 주석처리를 한다 System.out.println조심
		
		return "user/update";
	}
	
}
