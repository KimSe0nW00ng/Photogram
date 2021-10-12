package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.handler.ex.CustomvalidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드 DI 할때 사용
@Controller//1. IoC 2. 파일을 리턴하는 컨트롤러 
public class AuthController {
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//회원가입 버튼 -? /auth/signup -> /auth/signin
	@PostMapping("/auth/signup")
	public  String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-form-urlencoded방식)
		//@ResponseBody가 앞에 붙어있으면 데이터를 응답함 @Controller가 있더라도
		if(bindingResult.hasErrors()) {
			//에러가 발생을 하면 모이는 장소
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomvalidationException("유효성 검사 실패함", errorMap);  //강제로 예외처리
			
		}else {
			User user = signupDto.toEntity();
			User userEntity = authService.회원가입(user);
			log.info(user.toString());
			System.out.println(userEntity);
			return "auth/signin";
		}
		
		
	}

}
