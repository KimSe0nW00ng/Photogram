package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.Subscribe.Subscribe;
import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.likes.Likes;
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
public class Image { // N : 1

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	private String caption; //오늘 나 너무 피곤해~~~
	private String postimageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert
	
	@JsonIgnoreProperties({"images"}) //images는 무시해라
	@JoinColumn(name = "userid")
	@ManyToOne
	private User user; // 1 : 1
	
	//이미지 좋아요 ~~
	@JsonIgnoreProperties({"image"}) // Likes에서 무한 참조 방지위해!
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	//댓글 ~~
	
	private LocalDateTime createDate;
	
	@Transient // DB에 컬럼이 만들어지지 않는다
	private boolean likeState;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	// 오브젝트를 콘솔에 출력할 때 문제가 될 수 있으니 User부분을 출력되지 않게 함. user삭제
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postimageUrl=" + postimageUrl
//				+ ", createDate=" + createDate + "]";
//	}
	
}
