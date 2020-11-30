package com.spring.board.service;

import java.util.List;
import java.util.Map;

import com.spring.board.model.BoardVO;
import com.spring.board.model.CommentVO;
import com.spring.board.model.MemberVO;
import com.spring.board.model.TestVO;

public interface InterBoardService {

	int test_insert(); // model단(BoardDAO)에 존재하는 메소드( test_insert() )를 호출 한다.   

	List<TestVO> test_select();		// model단에 존재하는 메소드

	int test_insert(Map<String, String> paraMap);	// model단(BoardDAO)에 존재하는 메소드( Map<String, String> paraMap )를 호출 한다.   

	int test_insert(TestVO vo);		// model단(BoardDAO)에 존재하는 메소드( Map<String, String> paraMap )를 호출 한다.

	List<Map<String, String>> test_employees();

	List<String> getImagefilenameList();

	MemberVO getLoginMember(Map<String, String> paraMap);	// 로그인 처리

	int add(BoardVO boardvo);	// 게시판 글쓰기

	List<BoardVO> boardListNoSearch();

	BoardVO getView(String seq, String userid);

	BoardVO getViewWithNoAddCount(String seq);

	int edit(BoardVO boardvo);

	int del(BoardVO boardvo);
	
	int addComment(CommentVO commentvo) throws Throwable;	// 댓글쓰기 (Transaction 처리)
	
	

}
