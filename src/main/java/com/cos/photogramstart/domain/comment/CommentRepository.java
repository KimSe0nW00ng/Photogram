package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	/*
	 * @Modifying
	 * 
	 * @Query(
	 * value="INSERT INTO commnet(content, imageid, userid, createDate) VALUES(:content, :imageid, :userid, now())"
	 * ,nativeQuery = true) Comment mSave(String content, int imageid, int userid);
	 */
	//@Modifying쿼리는 int형 만리턴 받을 수 있다.
}
