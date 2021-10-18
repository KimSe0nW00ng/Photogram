package com.cos.photogramstart.domain.comment;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data // 게터세터 // toString()도 자동 생성이 된다
@Entity //DB에 테이블을 생성
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	@Column(length = 100, nullable = false) // 글자 제한, null제한
	private String content;// 내용
	
	//무엇인가를 select할때 따라오는것이 1개라면 기본적으로 EAGER전략을 사용하고 반대 경우에는 LAZY전략을 사용 
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userid")
	@ManyToOne(fetch = FetchType.EAGER) // 한명은 여러개 댓글
	private User user;
	
	@JoinColumn(name = "imageid")
	@ManyToOne(fetch = FetchType.EAGER) // 한개 이미지는 여러개 댓글 // fetch = FetchType.EAGER사용시 오류로 변경!!
	private Image image; 
	
	private LocalDateTime createDate;

	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();

	}

}
