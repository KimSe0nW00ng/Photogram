package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Data // 게터세터
@Entity //DB에 테이블을 생성
@Table( // 두개를 복합적으로 유니크 키를 걸때 쓰는방법
		uniqueConstraints = {
				@UniqueConstraint(
						name = "likes_uk",
						columnNames = {"imageid","userid"} // 실제 데이터베이스에서 쓰는 컬럼명
						)
				
		}
		)
public class Likes { //N
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	//무한 참조됨
	@JoinColumn(name="imageid")
	@ManyToOne
	private Image image; // 1
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userid")
	@ManyToOne
	private User user; // 1
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	//nativeQuery를 쓰면 안들어간다
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
