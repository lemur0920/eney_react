<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,500,700,900" rel="stylesheet">
</head>
<body style="margin: 0; padding: 0; font-family: 'Noto Sans KR', sans-serif; color: #383838;">
<article style="width: 725px; border-top: 7px solid #37afe5;">
    <section style="padding: 30px; box-sizing: border-box;">
        <div style="display: inline-block;">
            <a href="http://www.eney.co.kr" target="_blank"><img src="https://www.eney.co.kr/resources/img/service/mail/logo.png" alt="에네이 로고" /></a>
            <h1 style="font-size: 36px; letter-spacing: -1.5px; margin: 14px 0 40px 0; line-height: 1.4;">주식회사 에네이 입니다.</h1>
            <span style="color: #909090; font-size: 18px; line-height: 22px;"><strong style="color: #37afe5;">${userInfo.name}</strong>님의 ${expiredInfo.count}개의 ${expiredTerm} <br>만료되어 알려드립니다.</span>
        </div>
        <div style="display: inline-block; float: right; margin-top: 30px;">
            <img src="https://www.eney.co.kr/resources/img/service/mail/mail.png" alt="" />
        </div>
    </section>
    <section style="padding: 30px; box-sizing: border-box; margin: 10px 0;">
        <table style="border-collapse: collapse; width: 100%;">
            <colgroup>
                <col style="width: 50%;">
                <col style="width: 50%;">
            </colgroup>
            <tbody>
            <tr style="border-top: 1px solid #e6e6e6;">
                <th style="border-right: 1px solid #e6e6e6; background-color: #fafafa; padding: 10px 0; font-size: 14px;">ID</th>
                <td style="text-align: center; font-size: 14px;">${userInfo.userid}</td>
            </tr>
            <tr style="border-top: 1px solid #e6e6e6;">
                <th style="border-right: 1px solid #e6e6e6; background-color: #fafafa; padding: 10px 0; font-size: 14px;">만료일</th>
                <td style="text-align: center; font-size: 14px;">${expiredInfo.ldate}</td>
            </tr>
            </tbody>
        </table>
        <p style="font-size: 14px; padding-top: 15px;">
            등록하신 서비스의 사용 연장은<br>서비스 신청을 통해 이용하실 수 있습니다.
        </p>
    </section>
    <section style="border-top: 1px solid #e6e6e6; background-color: #fafafa;">
        <div style="padding: 30px;">
            <a href="http://www.eney.co.kr" target="_blank" style="display: inline-block; padding-right: 30px; vertical-align: top; margin-top: 10px;"><img src="https://www.eney.co.kr/resources/img/service/mail/logo.png" alt="에네이 로고" /></a>
            <ul style="list-style: none; padding: 0; display: inline-block; font-size: 13px; color: #909090;">
                <li>법인명(상호) : 주식회사 에네이  대표자(성명) : 전재혁</li>
                <li>사업자등록번호 : 119-87-01395  통신판매번호 : 제2015-서울동작-0592호 주식회사 에네이</li>
                <li>전화 : 0506-191-0024  팩스 : 070-7815-2201</li>
                <li>주소 : 서울특별시 동작구 상도로 369 (상도동) 벤처중소기업센터 103호</li>
                <li>개인정보관리책임자 : 이한슬 (hanseullee@eney.co.kr)</li>
                <li>Copyright ⓒ에네이 2014~2019 All Rights Reserved</li>
            </ul>
            <div>
    </section>
</article>
</body>
</html>