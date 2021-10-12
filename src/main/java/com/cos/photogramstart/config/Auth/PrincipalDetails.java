package com.cos.photogramstart.config.Auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.User.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user =user;
	}

	//권한 : 한개가 아닐 수 있음 (ex.3개 이상의 권한자 있음)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//권한을 가져오는 함수 (User)에 권한있음
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(()-> {return user.getRole();});
		return collector;
	}// 람다식 사용 (함수를 넘기는 목적으로 사용)

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정의 만료여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { //계정 잠금여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //비밀번호 변경기간 여부
		return true;
	}

	@Override
	public boolean isEnabled() { //계정 활성화 여부
		return true;
	}

}
