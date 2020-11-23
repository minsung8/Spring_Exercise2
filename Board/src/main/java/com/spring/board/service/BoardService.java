package com.spring.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.model.InterBoardDAO;

//=== #31. Service 선언 === 
//트랜잭션 처리를 담당하는곳 , 업무를 처리하는 곳, 비지니스(Business)단
@Service
public class BoardService implements InterBoardService {
	
	// === #34. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired
	private InterBoardDAO dao;
	// Type 에 따라 Spring 컨테이너가 알아서 bean 으로 등록된 com.spring.model.BoardDAO 의 bean 을  dao 에 주입시켜준다. 
	// 그러므로 dao 는 null 이 아니다.

	@Override
	public int test_insert() {
		
		int n = dao.test_insert();
		
		return n;
}
	

}
