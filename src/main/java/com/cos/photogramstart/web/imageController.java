package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomvalidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class imageController {

	
	private final ImageService imageService;
	
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
		//이미지 업로드 할때 필요한 dto와 로그인 세션
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomvalidationException("이미지가 첨부되지 않았습니다", null);
		}
		
		
		//서비스 호출
		imageService.사진업로드(imageUploadDto, principalDetails);
		
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
}

