<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<% String ctxPath = request.getContextPath(); %>
    
<style type="text/css">
   table, th, td {border: solid 1px gray;}

    #table {width: 970px; border-collapse: collapse;}
    #table th, #table td {padding: 5px;}
    #table th {background-color: #DDD;}
     
    .subjectStyle {font-weight: bold;
                   color: navy;
                   cursor: pointer;} 
</style>

<script type="text/javascript">

   $(document).ready(function(){
      
	   $("span.subject").bind("mouseover", function() {
			$(this).addClass("subjectStyle");
	   });  
	   
	   $("span.subject").bind("mouseout", function() {
			$(this).removeClass("subjectStyle");
	   });  
	   
    });// end of $(document).ready(function(){})-------------------
    
    function goView(seq) {
    	
    	location.href="<%= ctxPath%>/view.action?seq="+seq;
    	
    }
         
</script>
   
<div style="padding-left: 3%;">
   <h2 style="margin-bottom: 30px;">글목록</h2>
   
   <table id="table">
      <tr>
         <th style="width: 70px;  text-align: center;">글번호</th>
         <th style="width: 360px; text-align: center;">제목</th>
         <th style="width: 70px;  text-align: center;">성명</th>
         <th style="width: 150px; text-align: center;">날짜</th>
         <th style="width: 70px;  text-align: center;">조회수</th>
      </tr>   
      <c:forEach var="boardvo" items="${boardList}" varStatus="status">
         <tr>
            <td align="center">${boardvo.seq}</td>
            <td align="left">
                <c:if test="${boardvo.commentCount > 0}">
               		<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject} 
               		<span style="color:red;">[${boardvo.commentCount}]</span></span>
                </c:if>
                <c:if test="${boardvo.commentCount == 0}">
               		<span class="subject" onclick="goView('${boardvo.seq}')">${boardvo.subject}</span>
                </c:if>
            </td>
            <td align="center">${boardvo.name}</td>
            <td align="center">${boardvo.regDate}</td>
            <td align="center">${boardvo.readCount}</td>
            </tr>
      </c:forEach>
   </table>
   
</div>

