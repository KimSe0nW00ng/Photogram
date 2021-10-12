package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.handler.ex.CustomvalidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController//데이터 응답
public class UserApiController {

	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@GetMapping("/api/user/{pageUserid}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserid, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(),pageUserid);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 가져오기 성공",subscribeDto),HttpStatus.OK);
		
	}
		
	
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto, 
			BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음!! 파라미터에 적어야됨
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {// Validation 체크
			//에러가 발생을 하면 모이는 장소
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				
//				System.out.println("=============================");
//				errorMap.put(error.getField(), error.getDefaultMessage());
//				System.out.println("=============================");
				// 양방향으로 계속적으로 호출되기 때문에 오류가 난다.
			}
			throw new CustomvalidationApiException("유효성 검사 실패함", errorMap);  //강제로 예외처리
			
		}else {
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity); //세션 정보 변경
			return new CMRespDto<>(1,"회원 수정 완료", userEntity); 
			//응답시에 userEntity 의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다.
			
			
		}
		
		
	}
}
