<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>결제 오류</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<link href="css/css_admin.css" rel="stylesheet" type="text/css">
	<link href="css/css_01.css" rel="stylesheet" type="text/css">
	<script>		
	    alert("취소되었습니다.\n처음부터 다시 신청해주세요.");
	    opener.opener.window.location.href= "${cp}/";
		window.opener.open('','_self').close();
		window.open('','_parent').close();		
	</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table style = "width:500px;border:none;">
	<!--title-->
	<tr>
		<td height="54" background="images/manager_title01.gif"	style="padding-left: 65px; padding-top: 18px"><font size="3"><strong>결제 오류</strong></font></td>
	</tr>
	<!--title-->
	<tr>
		<td>${message }</td>
	</tr>
	<tr>
		<td align="center"><!--본문테이블 시작--->
		<table style="background:#B0B0B0;border:none;width:450px;cellpadding:4; cellspacing:1;">	
			<tr>
				<td width="100" align="center" bgcolor="#F6F6F6"><b>주문번호</b></td>
				<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b>${message}</b></td>								
			</tr>
			<tr>
				<td width="100" align="center" bgcolor="#F6F6F6"><b>거래번호</b></td>
				<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b><c:out value="${payResponse.transactionId }"/></b></td>								
			</tr>
			<tr>
				<td width="100" align="center" bgcolor="#F6F6F6"><b>응답코드</b></td>
				<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b><c:out value="${payResponse.responseCode }"/></b></td>								
			</tr>
			<tr>
				<td width="100" align="center" bgcolor="#F6F6F6"><b>응답메시지</b></td>
				<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b><c:out value="${payResponse.responseMessage }"/></b></td>								
			</tr>
			<tr>
				<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답코드</b></td>
				<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b><c:out value="${payResponse.detailResponseCode }"/></b></td>								
			</tr>
			<tr>
				<td width="100" align="center" bgcolor="#F6F6F6"><b>상세응답메시지</b></td>
				<td width="200" align="left" bgcolor="#FFFFFF">&nbsp;<b><c:out value="${payResponse.detailResponseMessage }"/></b></td>								
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>