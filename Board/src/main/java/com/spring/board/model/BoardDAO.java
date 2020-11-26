package com.spring.board.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//=== #32. DAO 선언 ===
@Repository 
public class BoardDAO implements InterBoardDAO {

	// === #33. 의존객체 주입하기(DI: Dependency Injection) ===
	// >>> 의존 객체 자동 주입(Automatic Dependency Injection)은
	//     스프링 컨테이너가 자동적으로 의존 대상 객체를 찾아서 해당 객체에 필요한 의존객체를 주입하는 것을 말한다. 
	//     단, 의존객체는 스프링 컨테이너속에 bean 으로 등록되어 있어야 한다. 

	//     의존 객체 자동 주입(Automatic Dependency Injection)방법 3가지 
	//     1. @Autowired ==> Spring Framework에서 지원하는 어노테이션이다. 
	//                       스프링 컨테이너에 담겨진 의존객체를 주입할때 타입을 찾아서 연결(의존객체주입)한다.
	
	//     2. @Resource  ==> Java 에서 지원하는 어노테이션이다.
	//                       스프링 컨테이너에 담겨진 의존객체를 주입할때 필드명(이름)을 찾아서 연결(의존객체주입)한다.
	
	//     3. @Inject    ==> Java 에서 지원하는 어노테이션이다.
    //                       스프링 컨테이너에 담겨진 의존객체를 주입할때 타입을 찾아서 연결(의존객체주입)한다.	
	
	@Resource
	private SqlSessionTemplate sqlsession;			// 로컬 디비에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
    // 그러므로 sqlsession 는 null 이 아니다.
	
	@Resource
	private SqlSessionTemplate sqlsession2;			// 원격 디비에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
    // 그러므로 sqlsession2 는 null 이 아니다.
	
	@Resource
	private SqlSessionTemplate sqlsession3;			// 원격 디비에 연결
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
    // 그러므로 sqlsession2 는 null 이 아니다.
	
	/*
	 * @Autowired private SqlSessionTemplate abc; // Type 에 따라 Spring 컨테이너가 알아서
	 * root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을 abc 에
	 * 주입시켜준다. // 그러므로 abc 는 null 이 아니다.
	 */
	
	// spring_test 테이블에 insert 하기 
	@Override
	public int test_insert() {
		int n = sqlsession.insert("board.test_insert");
		return n;
	}

	@Override
	public List<TestVO> test_select() {

		List<TestVO> testvoList = sqlsession.selectList("board.test_select");
		
		return testvoList;
	}

	@Override
	public int test_insert(Map<String, String> paraMap) {
		
		int n = sqlsession.insert("board.test_insert_map", paraMap);
		
		return n;
	}

	@Override
	public int test_insert(TestVO vo) {
		int n = sqlsession.insert("board.test_insert_vo", vo);
		return n;
	}
		
	@Override
	public List<Map<String, String>> test_employees() {
		List<Map<String, String>> empList = sqlsession3.selectList("hr.test_employees");
		return empList;
	}

	@Override
	public List<String> getImagefilenameList() {
		
		List<String> imagefilenameList = sqlsession.selectList("board.getImagefilenameList");
		
		return imagefilenameList;
	}
	
	// ==#46. 로그인 처리 === ///
	@Override
	public MemberVO getLoginMember(Map<String, String> paraMap) {
		
		MemberVO loginuser = sqlsession.selectOne("board.getLoginMember", paraMap);
		return loginuser;
		
	}

	@Override
	public int updateIdle(String userid) {
		
		int n = sqlsession.update("board.updateIdle", userid);
		
		return n;
		
	}

	@Override
	public int add(BoardVO boardvo) {

		int n = sqlsession.insert("board.add", boardvo);
		
		return n;
	}
	
	
	
	
	
	
	
	
	
	
	
}
