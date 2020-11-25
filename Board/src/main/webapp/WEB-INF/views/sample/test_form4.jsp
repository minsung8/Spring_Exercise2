<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
	// board
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">
	
	$(document).ready(function () {
		
		$("form[name=testFrm]").submit(function() {
			
			var noVal = $("input[name=no]").val();
			var nameVal = $("input[name=name]").val();
			
			if (noVal.trim() == "" || nameVal.trim() == "") {
				alert("모두 입력하세요!");
				return false;
			}
			
		});
		
	});
	
</script>

</head>
<body>
	<form name="testFrm" action="<%= ctxPath%>/test/modelAndview_insert.action" method="POST">
		번호 : <input type="text" name="no" /><br>
		성명 : <input type="text" name="name" /><br>
		<input type="submit" value="확인" /><br>
		<input type="reset" value="취소" /><br>
	</form>
</body>
</html>
