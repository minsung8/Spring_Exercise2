package com.spring.board.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
		
	
}

