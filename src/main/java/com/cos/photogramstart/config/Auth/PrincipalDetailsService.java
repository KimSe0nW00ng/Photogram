package com.cos.photogramstart.config.Auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.User.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //Service 붙이는 순간 IoC가됨
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	// 패스워드는 알아서 체킹하니까 신경 쓸 필요 없다.
	// 리턴이 잘 되면 자동으로 UserDetails 타입을 세션으로 만든다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { //못찾았다.
			return null;
		
		}else {
			return new PrincipalDetails(userEntity);
		}
	}

}
