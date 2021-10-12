package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper; // qlrm == 데이터베이스에서 result결과를 자바클래스에 매핑해주는 라이브러리(pom.xml에있음)
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.Subscribe.SubscribeRepositoty;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	//di
	private final SubscribeRepositoty subscribeRepositoty;
	private final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져있는 구현체
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalid, int pageUserid){
		
		//쿼리 준비
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileimageUrl, "); //마지막에 한칸을 꼭 띄워줘야함
		sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserid = ? AND toUserid = u.id), 1, 0) subscribeState, "); // ? = 로그인 한 아이디 principalid
		sb.append("if ((?=u.id), 1, 0) equalUserState "); // ? = 로그인 한 아이디 principalid
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserid ");
		sb.append("WHERE s.fromUserid = ?"); // 세미콜론 첨부 XX
		//마지막 물음표 = pageUserid
		
		//Binding
		 Query query = em.createNativeQuery(sb.toString())
				 .setParameter(1, principalid)
				 .setParameter(2, principalid)
				 .setParameter(3, pageUserid);
		 
		 //쿼리 실행 // qlrm == 데이터베이스에서 result결과를 자바클래스에 매핑해주는 라이브러리(pom.xml에있음)
		 JpaResultMapper result = new JpaResultMapper();
		 result.list(query, SubscribeDto.class); // Unique 유일한 1건 리턴
		 List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		 return  subscribeDtos;
	}
	
	
	@Transactional
	public void 구독하기(int fromUserid, int toUserid) {
		
		try {
			subscribeRepositoty.mSubscribe(fromUserid, toUserid);
			
		} catch (Exception e) {
			throw new CustomApiException("이미 구독하였습니다");
		}
	}

	@Transactional
	public void 구독취소하기(int fromUserid, int toUserid) { // 구독 취소하기는 오류날 가능성이 적음
			
			subscribeRepositoty.mUnSubscribe(fromUserid, toUserid);
	}
}
