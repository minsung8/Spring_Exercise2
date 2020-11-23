<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>페이지 로딩 성공</h1>
	
	<c:if test="${n eq 1 }">
			<span style="color:blue; font-size: 16pt;">${message}</span>
	</c:if>
	<c:if test="${n ne 1 }">
			<span style="color:red; font-size: 16pt;">${message}</span>
	</c:if>
	
	
	
</body>
</html>