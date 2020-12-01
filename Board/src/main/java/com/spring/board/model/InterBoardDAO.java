package com.spring.board.model;

import java.util.List;
import java.util.Map;

public interface InterBoardDAO {

	int test_insert();  // spring_test 테이블에 insert 하기 

	List<TestVO> test_select();		// spring_test 테이블에 select 하기 

	int test_insert(Map<String, String> paraMap);

	int test_insert(TestVO vo);

	List<Map<String, String>> test_employees();

	List<String> getImagefilenameList();

	MemberVO getLoginMember(Map<String, String> paraMap);
	int updateIdle(String userid);

	int add(BoardVO boardvo);		// 게시판 글쓰기

	List<BoardVO> boardListNoSearch();		// 글 전체 목록 보여주기

	void setAddReadCount(String seq);
	BoardVO getView(String seq);

	int edit(BoardVO boardvo);

	int del(BoardVO boardvo);

	int addComment(CommentVO commentvo);		// 댓글입력 및 count update
	int updateCommentCount(String parentSeq);

	int updateMemberPoint(Map<String, String> paraMap);

	List<CommentVO> getCommentList(String parentSeq);		// 댓글 읽어오기

	
	
	
	
	
}
