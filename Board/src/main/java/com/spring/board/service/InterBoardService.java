package com.spring.board.service;

import java.util.List;
import java.util.Map;

import com.spring.board.model.TestVO;

public interface InterBoardService {

	int test_insert(); // model단(BoardDAO)에 존재하는 메소드( test_insert() )를 호출 한다.   

	List<TestVO> test_select();		// model단에 존재하는 메소드

	int test_insert(Map<String, String> paraMap);	// model단(BoardDAO)에 존재하는 메소드( Map<String, String> paraMap )를 호출 한다.   

	int test_insert(TestVO vo);		// model단(BoardDAO)에 존재하는 메소드( Map<String, String> paraMap )를 호출 한다.

}
