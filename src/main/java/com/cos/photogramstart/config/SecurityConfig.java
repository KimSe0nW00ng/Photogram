package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // loC 
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder(); 
	}
	
	
	//요청에따라 구분하여 기능 제공
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //비정상적 접근자를 막기위한 토큰발급 시스템 끄기
		
		//super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨
		http.authorizeRequests()
		.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()
		//위의 주소로 끝나는 것들은 다 redirection해서 
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/auth/signin") // <- 페이지로 가게 하겠다 // GET일 경우 실행
		.loginProcessingUrl("/auth/signin")//POST 일 경우 실행(스프링 시큐리티가 로그인 프로세스 실행)
		.defaultSuccessUrl("/"); // <- 로그인이 성공하면 옆의 주소로 가겠다.
	}
}
