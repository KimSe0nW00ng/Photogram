package com.cos.photogramstart.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;


//어노테이션이 없어도 JpaRepository를 상속하면 IoC가 자동으로 등록됨
public interface UserRepository extends JpaRepository<User, Integer>{

	//JPA Query method
	
	User findByUsername(String username);
}
