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
	   
	   $("input#searchWord").keyup(function(event) {
		   if ( event.keyCode == 13 ){
			   goSearch();
		   }
	   });
	   
	   $("div#displayList").hide();
	   
	   $("input#searchWord").keyup(function() {
		   
		   var wordLength = $(this).val().length;
		   
		   if (wordLength == 0) {
			   $("div#displayList").hide();
		   } else {
			   $.ajax({
				   url:"<%= request.getContextPath()%>/wordSearchShow.action",
				   type:"GET",
				   data:{"searchType":$("select#searchType").val()
					   , "searchWord":$("input#searchWord").val()},
				   dataType:"JSON",
				   success:function(json) {
					   
					   <%-- === #112. 검색어 입력시 자동글 완성하기 --%>
					   if (json.length > 0) {
						   // 검색된 데이터가 있는 경우
						   
						   var html = "";
						   
						   $.each(json, function(index, item) {
							   var word = item.word;
							   
							   var index = word.toLowerCase().indexOf( $("input#searchWord").val() );;
							   var len = $("input#searchWord").val().length;
							   
							   // console.log(word.substr(0, index));
							   // console.log(word.substr(index, len));
							   // console.log(word.substr(index+len));
							   
							   var result = "<span style='color:blue;'>" + word.substr(0, index) +"</span><span style='color:red;'>" + word.substr(index, len) +"</span><span style='color:blue;'>" + word.substr(index+len) + "</span>";
							   
							   html += result + "<br>";
						   });
						   
						   $("div#displayList").html(html);
						   $("div#displayList").show();
					   } 
					   
				   },
				   error: function(request, status, error){
			        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			       }
					
			   });			   
		   }
		   

		   

		   
	   });
	   
    });// end of $(document).ready(function(){})-------------------
    
    function goView(seq) {
    	
    	location.href="<%= ctxPath%>/view.action?seq="+seq;
    	
    }
    
    function goSearch() {
    	
    	var frm = document.searchFrm;
    	frm.method = "GET";
    	frm.action = "<%= request.getContextPath()%>";
    	
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

	<!-- === #101. 글검색 폼 추가하기 === -->
	<form name="searchFrm" style="margin-top: 20px;">
      <select name="searchType" id="searchType" style="height: 26px;">
         <option value="subject">글제목</option>
         <option value="name">글쓴이</option>
      </select>
      <input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" /> 
      <button type="button" onclick="goSearch()">검색</button>
   </form>
   
   <!-- === #106. 검색글 입력시 자동글 완성하기 1 === -->
   <div id="displayList" style="border:solid 1px gray; border-top:0px; width:318px; height:100px; margin-left:70px; margin-top:-1px; overflow:auto;">
   		
   </div>
</div>

