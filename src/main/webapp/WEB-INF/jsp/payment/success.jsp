<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
	<title>상품 구매 완료</title>
	<script>
		alert("결제 완료 되었습니다.\n자세한 정보는 마이페이지에서 확인 가능합니다.");
		opener.opener.window.location.href= "${cp}/main.do";
		window.opener.open('','_self').close();
		window.open('','_parent').close();
		opener.opener.window.reload();
		
	</script>
</head>
<body>
	
</body>
</html>