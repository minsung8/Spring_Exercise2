package com.spring.board.model;

import java.util.List;
import java.util.Map;

public interface InterBoardDAO {

	int test_insert();  // spring_test 테이블에 insert 하기 

	List<TestVO> test_select();		// spring_test 테이블에 select 하기 

	int test_insert(Map<String, String> paraMap);

	int test_insert(TestVO vo);
	
	
	
}
