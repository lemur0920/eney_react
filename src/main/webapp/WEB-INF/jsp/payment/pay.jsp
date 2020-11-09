<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cp" value="${pageContext.request.contextPath}"></c:set>
 
<!DOCTYPE html>
<html>
<head>
	<title>결제요청</title>
	<link rel="stylesheet" type="text/css" href="${cp}/resources/css/common.css">
	<link rel="stylesheet" type="text/css" href="${cp}/resources/css/reset.css">
	<style>
		table { width:85%; margin:0 auto;}
		body { padding:0; margin:0}
		.info_wrap { border-top:15px solid #37afe5; }
		.info {padding:30px; font-size:14px;}
		img { display:block; margin:0 auto;}
		.pay_txt {text-align:center; padding:5px; font-size:18px; font-weight:500; margin-bottom:20px;}
		.info table tr {border-bottom:3px solid #fff;}
		.info table tr th {background:#f8f8f8; vertical-align:middle; width:166px; font-weight:500; padding:12px 0; font-size:15px;}
		.info table tr td{vertical-align:middle;text-align:left;padding:8px 10px; font-size:15px; font-weight:300;}
		.payment_pay_btn {text-align:center;}
		.payment_pay_btn input[type="submit"]{
			text-align:center;padding:15px 107px;cursor:pointer;color:#fff;border-radius:3px;font-size:16px;text-shadow:none;border:none;
			background: #37afe5; /* For browsers that do not support gradients */
   			/* background: -webkit-linear-gradient(#95cfe9, #66b7db); /* For Safari 5.1 to 6.0 */
   			background: -o-linear-gradient(#95cfe9, #66b7db); /* For Opera 11.1 to 12.0 */
  			background: -moz-linear-gradient(#95cfe9, #66b7db); /* For Firefox 3.6 to*/ */
		}
	</style>
	<script>
		function checkSubmit(){
			var HForm = document.payment;
			HForm.target = "payment";
		
			var option ="width=500,height=477,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,left=150,top=150";
			var objPopup = window.open("", "payment", option);
		
			if(objPopup == null){	//팝업 차단여부 확인
				alert("팝업이 차단되어 있습니다.\n팝업차단을 해제하신 뒤 다시 시도하여 주십시오.");
			}
		
			HForm.submit();
		}
	</script>
</head>
<!--Header끝-->
<body>
	<form action="${paymentRequestUrl}" name="payment" method="post" accept-charset="euc-kr">
	<div class="info_wrap">
		<div class="info">
			<img src="${cp}/resources/img/icon_important.gif" alt="" />
			<p class="pay_txt">결제정보 확인</p>
			<div class="table_info">
			<table>
				<tr class="info-group">
					<th class="info-label">구매자 ID</th>
					<td class="info-value">${payRequest.USER_ID }</td>
				</tr>
				<tr class="info-group">
					<th class="info-label">서비스 유형</th>
					<td class="info-value">${payRequest.ITEM_NAME }</td>
				</tr>
				<tr class="info-group">
					<th class="info-label">상품비고</th>
					<td class="info-value">${itemParam}</td>
				</tr>
				<tr class="info-group">
					<th class="info-label">결제방식</th>
					<td class="info-value">
						<c:if test = "${payMethod eq 'credit'}">
							신용카드
						</c:if>
						<c:if test = "${payMethod eq 'epoint'}">
							E-포인트
						</c:if>
					</td>
				</tr>
				<tr class="info-group">
					<th class="info-label">결제금액(VAT 포함)</th>
					<td class="info-value"><fmt:formatNumber value="${payRequest.AMOUNT }" pattern="#,###.##"/>원</td>
				</tr>
			</table>
			</div>
		</div>
		</div>
		
		<input type="hidden" name="SERVICE_ID" value="${payRequest.SERVICE_ID }">
		<input type="hidden" name="ORDER_ID" class="input" value="${payRequest.ORDER_ID}">
		<input type="hidden" name="ORDER_DATE" value="${payRequest.ORDER_DATE}">
		<input type="hidden" name="USER_ID" value="${payRequest.USER_ID}">
		<input type="hidden" name="USER_IP" value="${payRequest.USER_IP}">
		<input type="hidden" name="ITEM_CODE" value="${payRequest.ITEM_CODE}">
		<input type="hidden" name="ITEM_NAME" value="${payRequest.ITEM_NAME}">
		<input type="hidden" name="AMOUNT" value="${payRequest.AMOUNT }">
		<input type="hidden" name="RETURN_URL" value="${payRequest.RETURN_URL}">
		
		<c:if test="${payRequest.USING_TYPE != null }">
			<input type="hidden" name="USING_TYPE" value="${payRequest.USING_TYPE}">
		</c:if>
		<c:if test="${payRequest.CURRENCY != null }">
			<input type="hidden" name="CURRENCY" value="${payRequest.CURRENCY}">
		</c:if>
		<c:if test="${payRequest.CARD_TYPE != null }">
			<input type="hidden" name="CARD_TYPE" value="${payRequest.CARD_TYPE}">
		</c:if>
		<c:if test="${payRequest.DIRECT_USE != null }">
			<input type="hidden" name="USING_TYPE" value="${payRequest.DIRECT_USE}">
		</c:if>
		<c:if test="${payRequest.INSTALLMENT_PERIOD != null }">
			<input type="hidden" name="INSTALLMENT_PERIOD" value="${payRequest.INSTALLMENT_PERIOD}">
		</c:if>		
		<input type="hidden" name="CHECK_SUM" class="input" value="${payRequest.CHECK_SUM}">
		<div class = "payment_pay_btn">
			<input type="submit" value="결제" onclick="javascript: checkSubmit();"/>
		</div>
	</form>
</body>
</html>	