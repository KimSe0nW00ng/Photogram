package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	@Query(value ="SELECT * FROM image WHERE userId IN (SELECT toUserid FROM subscribe WHERE fromUserid = :principalid) ORDER BY id DESC", nativeQuery = true)
	//SELECT * FROM image WHERE userId IN (SELECT toUserid FROM subscribe WHERE fromUserid = 2);
	Page<Image> mStory(int principalid, Pageable pageable); // 페이징을 위해 pageable을 받아준다
	//페이징이 되었기 때문에 List가 아니라 Page로 리턴받아야된다
}
