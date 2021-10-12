package com.cos.photogramstart.web.dto.image;


import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.image.Image;

import lombok.Data;

@Data
public class ImageUploadDto {
	//@NotBlank 멀티타입에서는 지원이 안된다.
	
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postimageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postimageUrl(postimageUrl)
				.user(user)
				.build();
	}
}
