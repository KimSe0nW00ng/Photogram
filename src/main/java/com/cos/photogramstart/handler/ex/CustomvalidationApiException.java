package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomvalidationApiException extends RuntimeException{

	//객체를 구분할 때
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;

	public CustomvalidationApiException(String message) {
		super(message); 
	}
	
	public CustomvalidationApiException(String message, Map<String, String> errorMap) {
		super(message); //부모 클래스에 전달
		this.errorMap = errorMap;
	}

	
	public Map<String,String> getErrorMap(){
		return errorMap;
	}
}
