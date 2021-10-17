package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import com.sun.mail.handlers.image_gif;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	@Transactional(readOnly = true) // 좋은점 영속성 컨텍스트에서 변경 감지를 해서 더티체킹 진행 flush(반영)DB에 저장 등 ==>이걸 안하게됨 =>성능향상
	public Page<Image> 이미지스토리(int principalid, Pageable pageable){
		// ImageApiController에서 넘긴 pageable을 받는다
		Page<Image> images = imageRepository.mStory(principalid,pageable);
		
		
		//2(fff) 로그인 
		//images에 좋아요 상태 담기
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalid) { // 해당 이미지에 좋아요 한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교
					image.setLikeState(true);
				}
			});
			
		});
		return images;
	}
	
	
	
	@Value("${file.path}") // 경로 지정하는 어노테이션 - yml파일에 정보 있음
	private String uploadFolder;

	@Transactional // Service단에서 꼭 걸어줘야함 
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); // uuid를 통해 고유아이디 추가해서 파일 덮어씌움 해결

		String imageFileName = uuid + "-" + imageUploadDto.getFile().getOriginalFilename(); // 파일 이름이 업로드됨 ex 1.jpg
		// 똑같은 파일명으로 저장 할 경우 덮어씌워지게됨

		System.out.println("이미지 파일 이름:" + imageFileName);

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		// 통신, I/O ->예외 발생 가능성 있음
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());
		//-> 주석 풀면 오류나는 이유
		//ImgaeEntity 실행되면 게터들이 실행 됨(Image.java에서)
		//User 부분에서도 출력을 하게됨 = 무한참조가 일어남
		
	}
}