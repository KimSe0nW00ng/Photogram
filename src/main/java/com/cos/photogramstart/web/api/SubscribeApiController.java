package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

	private final SubscribeService subscirbeService;
	
	@PostMapping("/api/subscribe/{toUserid}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserid){
		// 누가 @AuthenticationPrincipal PrincipalDetails principalDetails(현재 로그인 한 사람)
		// 누구를 @PathVariable int id
		
		subscirbeService.구독하기(principalDetails.getUser().getId(), toUserid);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독하기 성공",null),HttpStatus.OK);
	}
	
	@DeleteMapping("/api/subscribe/{toUserid}")
	public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserid){
		subscirbeService.구독취소하기(principalDetails.getUser().getId(), toUserid);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독취소하기 성공",null),HttpStatus.OK);
	}
}
