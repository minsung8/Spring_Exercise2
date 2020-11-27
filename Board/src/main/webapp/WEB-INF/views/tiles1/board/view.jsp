<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
   table, th, td, input, textarea {border: solid gray 1px;}
   
   #table, #table2 {border-collapse: collapse;
                   width: 900px;
                  }
   #table th, #table td{padding: 5px;}
   #table th{width: 120px; background-color: #DDDDDD;}
   #table td{width: 750px;}
   .long {width: 470px;}
   .short {width: 120px;}
   
   .move {cursor: pointer;}
   .moveColor {color: #660029; font-weight: bold;}
   
   a {text-decoration: none !important;}
</style>

<script type="text/javascript">
   $(document).ready(function(){
      
      
      
   }); // end of $(document).ready(function(){})----------------
   
   
</script>

<div style="padding-left: 10%;">
   <h1>글내용보기</h1>
   
   <table id="table">
      <tr>
         <th>글번호</th>
         <td>${boardvo.seq}</td>
      </tr>
      <tr>
         <th>성명</th>
         <td>${boardvo.name}</td>
      </tr>
      <tr>
         <th>제목</th>
         <td>${boardvo.subject}</td>
      </tr>
      <tr>
         <th>내용</th>
         <td>
          <p style="word-break: break-all;">${boardvo.content}</p>
          <%-- 
               style="word-break: break-all; 은 공백없는 긴영문일 경우 width 크기를 뚫고 나오는 것을 막는 것임. 
                    그런데 style="word-break: break-all; 나 style="word-wrap: break-word; 은
                    테이블태그의 <td>태그에는 안되고 <p> 나 <div> 태그안에서 적용되어지므로 <td>태그에서 적용하려면
               <table>태그속에 style="word-wrap: break-word; table-layout: fixed;" 을 주면 된다.
          --%>
         </td>
      </tr>
      <tr>
         <th>조회수</th>
         <td>${boardvo.readCount}</td>
      </tr>
      <tr>
         <th>날짜</th>
         <td>${boardvo.regDate}</td>
      </tr>
      
      
   </table>
   
   <br/>
   
   <div style="margin-bottom: 1%;">이전글&nbsp;:&nbsp;<span class="move" onclick="javascript:location.href='view.action?seq=${boardvo.previousseq}'">${boardvo.previoussubject}</span></div>
   <div style="margin-bottom: 1%;">다음글&nbsp;:&nbsp;<span class="move" onclick="javascript:location.href='view.action?seq=${boardvo.nextseq}'">${boardvo.nextsubject}</span></div>
   
   <br/>
   
    
   <button type="button" onclick="javascript:location.href='<%= request.getContextPath()%>/list.action'">전체목록보기</button>
   
   
   <button type="button" onclick="javascript:location.href='<%= request.getContextPath()%>/edit.action?seq=${boardvo.seq}'">수정</button>
   <button type="button" onclick="javascript:location.href='<%= request.getContextPath()%>/del.action?seq=${boardvo.seq}'">삭제</button>
   
</div>
    
    
    
    
    
    
    
    
    