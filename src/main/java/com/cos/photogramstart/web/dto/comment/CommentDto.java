package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
	private String content;
	private int imageid;

	//toEntity필요없다
}
