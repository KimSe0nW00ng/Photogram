package com.cos.photogramstart.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	
	@GetMapping("/api/image")
	public ResponseEntity<?> imageStroy(@AuthenticationPrincipal PrincipalDetails principalDetails,
		@PageableDefault(size = 3) Pageable pageable	){ 
		// @Pageable 페이징이 가능해진다
		Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);
		//이미지 서비스로 principalid를 넘긴다
		return new ResponseEntity<>(new CMRespDto<>(1,"성공",images), HttpStatus.OK);
	}
} //api를 잘 만들고 service 호출
