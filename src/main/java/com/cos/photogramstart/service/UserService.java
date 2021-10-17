package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.Subscribe.SubscribeRepositoty;
import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.User.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomvalidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final SubscribeRepositoty subscribeRepositoty;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${file.path}") // 경로 지정하는 어노테이션 - yml파일에 정보 있음
	private String uploadFolder;
	
	@Transactional
	public User 회원프로필사진변경 (int principalid, MultipartFile profileImageFile) {
		UUID uuid = UUID.randomUUID(); // uuid를 통해 고유아이디 추가해서 파일 덮어씌움 해결

		String imageFileName = uuid + "-" + profileImageFile.getOriginalFilename(); // 파일 이름이 업로드됨 ex 1.jpg
		// 똑같은 파일명으로 저장 할 경우 덮어씌워지게됨

		System.out.println("이미지 파일 이름:" + imageFileName);

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		// 통신, I/O ->예외 발생 가능성 있음
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalid).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileimageUrl(imageFileName);
		
		return userEntity;
	}// 더티체킹으로 업데이트 됨
	
	@Transactional(readOnly=true) //변경감지를 안하게 됨 연산과정 생략 능률 향상~~
	public UserProfileDto 회원프로필(int pageUserid, int principalid) { // 해당 페이지의 아이디
		UserProfileDto dto = new UserProfileDto();
		
		// SELECT * FROM image WHERE userid = :userid;
		User userEntity = userRepository.findById(pageUserid).orElseThrow(()->{
			//Optional이기 때문에 orElseThrow사용
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserid == principalid); // 1은 페이지 주인, -1은 주인이 아님
		//(위)int일 경우 삼항연산자 사용 두개가 같으면 true 
		//타입이 boolean일 경우 필요 없음
		dto.setImageCount(userEntity.getImages().size());
		
		int subscribeState = subscribeRepositoty.mSubscribeState(principalid, pageUserid);
		int subscribeCount = subscribeRepositoty.mSubscribeCount(pageUserid);
		
		dto.setSubscribeState(subscribeState == 1); // 1이면 true
		dto.setSubscribeCount(subscribeCount);
		
		// Popular좋아요 갯수 표시
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
		return dto;
	}
	
	@Transactional //write일 경우 작성
	public User 회원수정(int id, User user) {
		//1. 영속화
		//----------------------------------
//		User userEntity = userRepository.findById(10).orElseThrow(new Supplier<IllegalArgumentException>() {
//
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("찾을수 없는 아이디");
//			}
//	
//		}); //get() 찾았다라는 증거
		//Optional 사용시 - 1.무조건 찾음 get() 2. 못찾으면 익셉션 발동 orElseThrow()
		//----------------------------------
		//람다식 변환 결과
		User userEntity = userRepository.findById(id).orElseThrow(()-> {return new CustomvalidationApiException("찾을수 없는 아이디");});
		
		//2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
		userEntity.setName(user.getName());
		
		String rqwPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rqwPassword);
		
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;
	}// 더티체킹이 일어나서 업데이트가 완료됨

}
