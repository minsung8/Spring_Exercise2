package com.spring.board.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.common.Sha256;
import com.spring.board.model.BoardVO;
import com.spring.board.model.MemberVO;
import com.spring.board.model.TestVO;
import com.spring.board.service.InterBoardService;

/*
	사용자 웹브라우저 요청(View)  ==> DispatcherServlet ==> @Controller 클래스 <==>> Service단(핵심업무로직단, business logic단) <==>> Model단[Repository](DAO, DTO) <==>> myBatis <==>> DB(오라클)           
	(http://...  *.action)                                  |                                                                                                                              
	 ↑                                                View Resolver
	 |                                                      ↓
	 |                                                View단(.jsp 또는 Bean명)
	 -------------------------------------------------------| 
	
	사용자(클라이언트)가 웹브라우저에서 http://localhost:9090/board/test_insert.action 을 실행하면
	배치서술자인 web.xml 에 기술된 대로  org.springframework.web.servlet.DispatcherServlet 이 작동된다.
	DispatcherServlet 은 bean 으로 등록된 객체중 controller 빈을 찾아서  URL값이 "/test_insert.action" 으로
	매핑된 메소드를 실행시키게 된다.                                               
	Service(서비스)단 객체를 업무 로직단(비지니스 로직단)이라고 부른다.
	Service(서비스)단 객체가 하는 일은 Model단에서 작성된 데이터베이스 관련 여러 메소드들 중 관련있는것들만을 모아 모아서
	하나의 트랜잭션 처리 작업이 이루어지도록 만들어주는 객체이다.
	여기서 업무라는 것은 데이터베이스와 관련된 처리 업무를 말하는 것으로 Model 단에서 작성된 메소드를 말하는 것이다.
	이 서비스 객체는 @Controller 단에서 넘겨받은 어떤 값을 가지고 Model 단에서 작성된 여러 메소드를 호출하여 실행되어지도록 해주는 것이다.
	실행되어진 결과값을 @Controller 단으로 넘겨준다.
*/

//=== #30. 컨트롤러 선언 === 
@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
     그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. 
     즉, 여기서 bean의 이름은 boardController 이 된다. 
     여기서는 @Controller 를 사용하므로 @Component 기능이 이미 있으므로 @Component를 명기하지 않아도 BoardController 는 bean 으로 등록되어 스프링컨테이너가 자동적으로 관리해준다. 
*/
@Controller
public class BoardController {

	@Autowired     // Type에 따라 알아서 Bean 을 주입해준다.
	private InterBoardService service;
	
	
	// ============= ***** 기초시작 ***** ============= //
	@RequestMapping(value="/test/test_insert.action")
	public String test_insert(HttpServletRequest request) {
		
		int n = service.test_insert();
		
		String message = "";
		
		if(n == 1) {
			message = "데이터 입력 성공!!";
		}
		else {
			message = "데이터 입력 실패!!";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("n", n);
		
		return "sample/test_insert";	
	//  /WEB-INF/views/sample/test_insert.jsp 페이지를 만들어야 한다.
	}
	
	@RequestMapping(value="/test/test_select.action")
	public String test_select(HttpServletRequest request) {
		
		List<TestVO> testvoList = service.test_select();
		
		request.setAttribute("testvoList", testvoList);
		
		return "sample/test_select";
	}
	
	// @RequestMapping(value="/test/test_form.action", method= {RequestMethod.POST})	=> POST 요청만 허락
	// @RequestMapping(value="/test/test_form.action", method= {RequestMethod.GET})		=> GET 방식만 허락
	@RequestMapping(value="/test/test_form.action")
	public String test_form(HttpServletRequest request) {
		
		String method = request.getMethod();
		
		if ("GET".equalsIgnoreCase(method)) {
			return "sample/test_form";
		} else {
			String no = request.getParameter("no");
			String name = request.getParameter("name");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("no", no);
			paraMap.put("name", name);
			
			int n = service.test_insert(paraMap);
			
			if (n == 1) {
				return "redirect:/test/test_select.action";	// 페이지 이동
			} else {
				return "redirect:/test/test_form.action";	// 페이지 이동
			}
			
		}
		
	}
		
		// @RequestMapping(value="/test/test_form.action", method= {RequestMethod.POST})	=> POST 요청만 허락
		// @RequestMapping(value="/test/test_form.action", method= {RequestMethod.GET})		=> GET 방식만 허락
		@RequestMapping(value="/test/test_form2.action")
		public String test_form2(HttpServletRequest request, TestVO vo) {
			
			String method = request.getMethod();
			
			if ("GET".equalsIgnoreCase(method)) {
				return "sample/test_form2";
			} else {
				
				int n = service.test_insert(vo);
				
				if (n == 1) {
					return "redirect:/test/test_select.action";	// 페이지 이동
				} else {
					return "redirect:/test/test_form2.action";	// 페이지 이동
				}
				
			}
					
		}
		
	// ============= ***** 기초끝 ***** ============= //
		
		@RequestMapping(value="/test/test_form3.action", method= {RequestMethod.GET})		// GET 방식만 허용
		public String test_form3() {

			return "sample/test_form3";
					
		}
		
		
		/*
	    @ResponseBody 란?
	     	메소드에 @ResponseBody Annotation이 되어 있으면 return 되는 값은 View 단 페이지를 통해서 출력되는 것이 아니라 
	    	return 되어지는 값 그 자체를 웹브라우저에 바로 직접 쓰여지게 하는 것이다. 일반적으로 JSON 값을 Return 할때 많이 사용된다. 
	    */
		
		@ResponseBody
		@RequestMapping(value="/test/ajax_insert.action",  method= {RequestMethod.POST})
		public String ajax_insert(HttpServletRequest request) {
			
				
			String no = request.getParameter("no");
			String name = request.getParameter("name");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("no", no);
			paraMap.put("name", name);
			
			int n = service.test_insert(paraMap);
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("n", n);
			
			return jsonObj.toString();
		}

		
		/*
	    @ResponseBody 란?
	     	메소드에 @ResponseBody Annotation이 되어 있으면 return 되는 값은 View 단 페이지를 통해서 출력되는 것이 아니라 
	    	return 되어지는 값 그 자체를 웹브라우저에 바로 직접 쓰여지게 하는 것이다. 일반적으로 JSON 값을 Return 할때 많이 사용된다.  
	   
	   		>>> 스프링에서 json 또는 gson을 사용한 ajax 구현시 데이터를 화면에 출력해 줄때 한글로 된 데이터가 '?'로 출력되어 한글이 깨지는 현상이 있다. 
	               	이것을 해결하는 방법은 @RequestMapping 어노테이션의 속성 중 produces="text/plain;charset=UTF-8" 를 사용하면 
	               	응답 페이지에 대한 UTF-8 인코딩이 가능하여 한글 깨짐을 방지 할 수 있다. <<< 
		 */
		@ResponseBody		// view 페이지가 필요 없음
		@RequestMapping(value="/test/ajax_select.action", produces="text/plain;charset=UTF-8")
		public String ajax_select() {
			
			
			List<TestVO> testvoList = service.test_select();
			
			JSONArray jsonArr = new JSONArray();
			
			if (testvoList != null) {
				for (TestVO vo : testvoList) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("no", vo.getNo());
					jsonObj.put("name", vo.getName());
					jsonObj.put("writeday", vo.getWriteday());
					
					jsonArr.put(jsonObj);
				}
					
			}
			
			return jsonArr.toString();
		}
		
		
		// 리턴타입을 string 대신에 modelandview 사용
		@RequestMapping(value="/test/modelAndview_insert.action")
		public ModelAndView modelAndview_insert(ModelAndView mav, HttpServletRequest request) {
			
			String method = request.getMethod();
			
			if ("GET".equalsIgnoreCase(method)) {
				mav.setViewName("/sample/test_form4");
			} else {
				String no = request.getParameter("no");
				String name = request.getParameter("name");
				
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("no", no);
				paraMap.put("name", name);
				
				int n = service.test_insert(paraMap);
				
				if (n == 1) { 
					
					/*
					
					List<TestVO> testvoList = service.test_select();
					
					mav.addObject("testvoList", testvoList);
					
					mav.setViewName("sample/test_select");		// view 단 페이지의 파일명 지정하기 
					 
					*/
					//======================= 또는 페이지 이동 ======================
					
					mav.setViewName("redirect:/test/test_select.action");
				}
				
			}

			return mav;
		}
		

		// == 데이터테이블즈(datatables) -- datatables 1.10.19 기반으로 작성  == //
		@RequestMapping(value="/test/employees.action")
		public ModelAndView test_employees(ModelAndView mav) {
		   
		   List<Map<String,String>> empList = service.test_employees();
		   
		   mav.addObject("empList", empList);
		   mav.setViewName("sample/employees");
		   //    /WEB-INF/views/sample/employees.jsp 파일을 생성한다.
		   
		   return mav;
		}

		@RequestMapping(value="/test/employees_tiles1.action")
		public ModelAndView employees_tiles1(ModelAndView mav) {
		   
		   List<Map<String,String>> empList = service.test_employees();
		   
		   mav.addObject("empList", empList);
		   mav.setViewName("sample/employees.tiles1");
		   //   /WEB-INF/views/tiles1/sample/employees.jsp 파일을 생성한다.
		   
		   return mav;
		}

		@RequestMapping(value="/test/employees_tiles2.action")
		public ModelAndView employees_tiles2(ModelAndView mav) {
		   
		   List<Map<String,String>> empList = service.test_employees();
		   
		   mav.addObject("empList", empList);
		   mav.setViewName("sample/employees.tiles2");
		   //   /WEB-INF/views/tiles2/sample/employees.jsp 파일을 생성한다. 
		   
		   return mav;
		}
		
		// #36. 메인 페이지 요청
		@RequestMapping(value="/index.action")
		public ModelAndView index(ModelAndView mav) {
			
			List<String> imgfilenameList = service.getImagefilenameList();
			mav.addObject("imgfilenameList", imgfilenameList);
			mav.setViewName("main/index.tiles1");
			
			
			return mav;
		}
		
		// #40. 로그인 폼 페이지 요청
		@RequestMapping(value="/login.action", method={RequestMethod.GET})
		public ModelAndView login(ModelAndView mav) {
			
			mav.setViewName("login/loginform.tiles1");
			//
			
			return mav;
			
		}
		
		// === #41. 로그인 처리하기 === // 
		   @RequestMapping(value="/loginEnd.action", method= {RequestMethod.POST}) 
		   public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {
		      
		      String userid = request.getParameter("userid");
		      String pwd = request.getParameter("pwd");
		      
		      Map<String,String> paraMap = new HashMap<>();
		      paraMap.put("userid", userid);
		      paraMap.put("pwd", Sha256.encrypt(pwd));
		      
		      MemberVO loginuser = service.getLoginMember(paraMap); 
		      
		      if(loginuser == null) { // 로그인 실패시 
		         String message = "아이디 또는 암호가 틀립니다.";
		         String loc = "javascript:history.back()";
		         
		         mav.addObject("message", message);
		         mav.addObject("loc", loc);
		         
		         mav.setViewName("msg");
		         //   /WEB-INF/views/msg.jsp 파일을 생성한다.
		      }
		      
		      else { // 아이디와 암호가 존재하는 경우 
		         
		         if(loginuser.getIdle() == 1) { // 로그인 한지 1년이 경과한 경우 
		            String message = "로그인을 한지 1년지 지나서 휴면상태로 되었습니다. 관리자가에게 문의 바랍니다.";
		            String loc = request.getContextPath()+"/index.action";
		            // 원래는 위와같이 index.action 이 아니라 휴면인 계정을 풀어주는 페이지로 잡아주어야 한다.
		            
		            mav.addObject("message", message);
		            mav.addObject("loc", loc);
		            mav.setViewName("msg");
		         }
		         
		         else { // 로그인 한지 1년 이내인 경우 
		         
		            // !!!! session(세션) 이라는 저장소에 로그인 되어진 loginuser 을 저장시켜두어야 한다.!!!! //
		            // session(세션) 이란 ? WAS 컴퓨터의 메모리(RAM)의 일부분을 사용하는 것으로 접속한 클라이언트 컴퓨터에서 보내온 정보를 저장하는 용도로 쓰인다. 
		            // 클라이언트 컴퓨터가 WAS 컴퓨터에 웹으로 접속을 하기만 하면 무조건 자동적으로 WAS 컴퓨터의 메모리(RAM)의 일부분에 session 이 생성되어진다.
		            // session 은 클라이언트 컴퓨터 웹브라우저당 1개씩 생성되어진다. 
		            // 예를 들면 클라이언트 컴퓨터가 크롬웹브라우저로 WAS 컴퓨터에 웹으로 연결하면 session이 하나 생성되어지고 ,
		            // 또 이어서 동일한 클라이언트 컴퓨터가 엣지웹브라우저로 WAS 컴퓨터에 웹으로 연결하면 또 하나의 새로운 session이 생성되어진다. 
		            /*
		                  -------------
		                  | 클라이언트    |             ---------------------
		                  | A 웹브라우저 | ----------- |   WAS 서버              |
		                  -------------             |                  |
		                                            |  RAM (A session) |
		                  --------------            |      (B session) | 
		                  | 클라이언트       |           |                  |
		                  | B 웹브라우저   | ---------- |                  |
		                  ---------------           --------------------
		                  
		              !!!! 세션(session)이라는 저장 영역에 loginuser 를 저장시켜두면
		                   Command.properties 파일에 기술된 모든 클래스 및  모든 JSP 페이지(파일)에서 
		                        세션(session)에 저장되어진 loginuser 정보를 사용할 수 있게 된다. !!!! 
		                        그러므로 어떤 정보를 여러 클래스 또는 여러 jsp 페이지에서 공통적으로 사용하고자 한다라면
		                        세션(session)에 저장해야 한다.!!!!          
		             */
		            
		            HttpSession session = request.getSession(); 
		            // 메모리에 생성되어져 있는 session을 불러오는 것이다.
		            
		            session.setAttribute("loginuser", loginuser);
		            // session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.
		            
		            if(loginuser.isRequirePwdChange() == true) { // 암호를 마지막으로 변경한것이 3개월이 경과한 경우 
		               String message = "비밀번호를 변경하신지 3개월이 지났습니다. 암호를 변경하세요!!";
		               String loc = request.getContextPath()+"/index.action";
		               // 원래는 위와같이 index.action 이 아니라 사용자의 암호를 변경해주는 페이지로 잡아주어야 한다.
		               
		               mav.addObject("message", message);
		               mav.addObject("loc", loc);
		               mav.setViewName("msg");
		            }
		            
		            else { // 암호를 마지막으로 변경한것이 3개월 이내인 경우 
		               
		               // 막바로 페이지 이동을 시킨다. 
		               
		               // 특정 제품상세 페이지를 보았을 경우 로그인을 하면 시작페이지로 가는 것이 아니라
		               // 방금 보았던 특정 제품상세 페이지로 가기 위한 것이다.
		               String goBackURL = (String) session.getAttribute("goBackURL");
		               // shop/prodView.up?pnum=66
		               // 또는 null
		               
		               if(goBackURL != null) {
		                  mav.setViewName("redirect:/"+goBackURL);
		                  session.removeAttribute("goBackURL"); // 세션에서 반드시 제거해주어야 한다.
		               }
		               else {
		                  mav.setViewName("redirect:/index.action");   
		               }
		            
		            }   
		            
		          }
		         
		      }
		      
		      return mav;
		   }
		   
		   
	// === #50. 로그아웃 처리하기 === //
		   
		@RequestMapping(value="/logout.action")
		public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
			
			HttpSession session = request.getSession(); // 세션불러오기
			session.invalidate();
			
			String message = "로그아웃 되었습니다";
            String loc = request.getContextPath()+"/index.action";
			
			mav.addObject("message", message);
            mav.addObject("loc", loc);
            mav.setViewName("msg");
            
            return mav;
		}
		
		
		// === #51. 게시판 글쓰기 폼페이지 요청 === //
		@RequestMapping(value="/add.action")
		public ModelAndView add(ModelAndView mav, HttpServletRequest request) {

			mav.setViewName("board/add.tiles1");
            
            return mav;
            
		}
		
		// === #54. 게시판 글쓰기 완료 요청 === //
		@RequestMapping(value="/addEnd.action", method= {RequestMethod.POST} )
		public String addEnd(BoardVO boardvo) {

			int n = service.add(boardvo);
			
            return "redirect:/list.action";
            
		}
		
}












