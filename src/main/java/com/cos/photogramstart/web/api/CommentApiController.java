package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomvalidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class CommentApiController {
	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(@Valid@RequestBody CommentDto commentDto,BindingResult bindingResult ,@AuthenticationPrincipal PrincipalDetails principalDetails){ // @RequestBody JSON데이터를 받아올수 있도록
		
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
			
		}
		
		Comment comment = commentService.댓글쓰기(commentDto.getContent(),commentDto.getImageid(), principalDetails.getUser().getId()); // content, imageid, userid
		return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기성공", comment),HttpStatus.CREATED) ;
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.댓글삭제(id);
		return new ResponseEntity<>(new CMRespDto<>(1,"댓글삭제성공", null),HttpStatus.OK) ;
	}
}
