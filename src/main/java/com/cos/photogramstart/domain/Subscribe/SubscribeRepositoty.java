package com.cos.photogramstart.domain.Subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepositoty extends JpaRepository<Subscribe, Integer> {
																									//Subscribe 모델만 리턴한다
	@Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
	@Query(value = "INSERT INTO subscribe(fromUserid, toUserid, createDate) VALUES(:fromUserid, :toUserid, now())",nativeQuery =  true)
	void mSubscribe(int fromUserid, int toUserid);
	// int인 이유 1(변경된 행의 개수가 리턴됨) 성공 -1 실패
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserid = :fromUserid AND toUserid = :toUserid", nativeQuery =  true)
	void mUnSubscribe(int fromUserid, int toUserid);
	
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserid = :principalid AND toUserid = :pageUserid", nativeQuery = true)
	int mSubscribeState(int principalid, int pageUserid);//로그인한 아이디, 페이지 아이디
	//COUNT = 1 구독 상태
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserid = :pageUserid", nativeQuery = true)
	int mSubscribeCount(int pageUserid);
	//COUNT = 숫자 구독한 구독자 수
}
