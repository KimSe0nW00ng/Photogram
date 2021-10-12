package com.cos.photogramstart.web.dto.auth;



import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.User.User;

import lombok.Data;

@Data // 게터세터 어노테이션
public class SignupDto {
	@Size(min = 2, max=20) //컬럼 사이즈 지정
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
				
	}
}
