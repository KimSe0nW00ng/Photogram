package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.User.User;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	
	
	@Transactional
	public Comment 댓글쓰기(String content, int imageid ,int userid) {
		
		//Tip (객체를 만들 때 id값만 담아서 insert 할 수 있따.)
		Image image = new Image();
		image.setId(imageid);
		User user = new User();
		user.setId(userid);
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(user);
		
		return commentRepository.save(comment);
	}
	
	public void 댓글삭제() {
		
	}
}
