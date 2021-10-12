package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	@Query(value ="SELECT * FROM image WHERE userId IN (SELECT toUserid FROM subscribe WHERE fromUserid = :principalid);", nativeQuery = true)
	//SELECT * FROM image WHERE userId IN (SELECT toUserid FROM subscribe WHERE fromUserid = 2);
	List<Image> mStory(int principalid);
}
