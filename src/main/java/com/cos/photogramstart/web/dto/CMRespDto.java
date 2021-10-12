package com.cos.photogramstart.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//공통적으로 쓰이는 응답용 DTO



@AllArgsConstructor
@NoArgsConstructor
@Data //게터세터
public class CMRespDto<T> { //<T> 제네릭 - 타입을 나중에 확정하는방법

	private int code; // 1(성공), -1(실패)
	private String message;
	private T data;
	
	
}
