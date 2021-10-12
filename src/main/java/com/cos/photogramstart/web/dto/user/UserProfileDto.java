package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.User.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// 이 클래스 없이도 사진등록부분 해결 가능하다
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	
	private boolean pageOwnerState; // 주인인지 아닌지?//JSTL에서 앞에 is가 붙으면 파싱이 잘 안된다
	private User user;
	private int imageCount; // 최대한 뷰 페이지에서 연산하는것을 줄이는 것이 좋다
	private boolean subscribeState;//구독 상태
	private int subscribeCount; // 구독 수
	//Repository에서 구독자 수를 알아야 한다
}
