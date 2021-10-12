package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.Util.Script;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomvalidationApiException;
import com.cos.photogramstart.handler.ex.CustomvalidationException;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController // 데이터반환
@ControllerAdvice // 모든 익셉션을 컨트롤함
public class ControllExceptionHandler {

	@ExceptionHandler(CustomvalidationException.class)
	public String validationException(CustomvalidationException e) {
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script좋음
		// 2. Ajax통신 - CMRespDto
		// 3.Android 통신 - CMRespDto
		if (e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
			
		}
	}

	@ExceptionHandler(CustomvalidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomvalidationApiException e) {
		System.out.println("+========================나시랳ㅇ/?");
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> ApiException(CustomApiException e) {
		System.out.println("+========================나시랳ㅇ/?");
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(CustomException.class)
	public String Exception(CustomException e) {
		return Script.back(e.getMessage());
	}
}
