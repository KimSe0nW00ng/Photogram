package com.cos.photogramstart.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API (자바로 데이터를 영구적으로 저장(DB)할 수 있는 API제공)
@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data // 게터세터
@Entity //DB에 테이블을 생성
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	@Column(length = 20, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website;// 웹사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileimageUrl;//사진
	private String role; // 권한
	
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지 마라
	// User을 Select할때 해당 User id로 등록된 image들을 다 가져와 라는 뜻
	// Lazy = User를 Select할때 해당 User id로 등록된 image들을 가져오지 마라 - 대신 getimages() 함수의 images들이 호출될 때 가져와
	// Eager = User를 Select할때 해당 User id로 등록된 image들을 전부 Join해서 가져와라
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY) //LAZY일 경우 User까지만 가져오고 컨트롤러단에서 세션이 종료되어 데이터를 가져오는것이 불가능하다
	@JsonIgnoreProperties({"user"}) // 아래 image에 있는 user는 무시하고 파싱을 해준다. 무한참조 해결//자주사용
	private List<Image> images; // 양방향 매핑
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
