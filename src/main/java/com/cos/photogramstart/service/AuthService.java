package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.User.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service// 1.IoC 2.트랜잭션 관리
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Write(Insert Update, Delete할때 사용
	public User 회원가입(User user) {
		//회원가입 진행
		String rqwPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rqwPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER"); // 관리자 ROLE_ADMIN
		User userEntity = userRepository.save(user);
		return userEntity; 
	}
}
