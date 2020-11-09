import React from 'react';
import big_patch_img from '../../asset/image/big_patch_img.jpg'
import big_patch_icon1 from '../../asset/image/big_patch_icon1.jpg'
import big_patch_icon2 from '../../asset/image/big_patch_icon2.jpg'
import big_patch_icon3 from '../../asset/image/big_patch_icon3.jpg'
import big_patch_icon5 from '../../asset/image/big_patch_icon5.jpg'
import big_patch_icon6 from '../../asset/image/big_patch_icon6.jpg'
import big_patch_icon7 from '../../asset/image/big_patch_icon7.jpg'
import {Link} from 'react-router-dom'

const BulkMessage = () => {
    return (
        <section className="sub_container">
            <div className="navi clfx">
                <ul className="clfx">
                    <li>Home</li>
                    <li>서비스</li>
                    <li>패치대량메세징</li>
                </ul>
            </div>

            <div className="sub_title_area clfx">
                <div className="img_area"><img src={big_patch_img} alt=""/></div>
                <div className="txt_area">
                    <h1 className="title_style_2">패치대량메세징</h1>
                    <p className="txt_style_2">
                        다이렉트마케팅의 시작 패치메세징, 문자, 알림톡, 친구톡을 활용한 마케팅에 최적화 되어있습니다. <br/>
                        데이터기반의 다양한 마케팅 솔루션과 유기적으로 결합하여 사용해 보세요! 마케팅의 전환율을 높일 수 있습니다.
                    </p>
                    <div className="btn_area">
                        <Link to="/price" className="basic-btn01 btn-sky-bg">서비스 이용하기</Link>
                    </div>
                </div>
            </div>

            <div className="use_method">
                <h2 className="title_style_3">활용법</h2>
                <ul className="list_style_1">
                    <li>
                        <h3>1. 문자를 활용한 마케팅 진행시 캠페인의 전환율을 측정이 필요한 고객</h3>
                        <p>단축URL을 활용하여 랜딩페이지로 유입되는 현황을 파악 할 수 있습니다.</p>
                    </li>
                    <li>
                        <h3>2. 문자, 알림톡, 친구톡을 활용하여 많은 비용을 지불하고 있는 고객</h3>
                        <p>
                            패치AI 채널추천 솔루션을 활용하여 문자와 친구톡으로 중복 발송되는 비용을 줄일 수 있습니다.
                        </p>
                    </li>
                    <li>
                        <h3>3. 다양한 마케팅 데이터와 결합하여 마케팅인사이트를 확보하고 싶은 고객</h3>
                        <p>문자 발송 전환율과 콘텐츠 또는 웹로그데이터와 결합하여 매출 전환율을 높일수 있습니다. </p>
                    </li>
                </ul>
            </div>

            <div className="function_list bd_none">
                <h2 className="title_style_3">주요기능</h2>
                <ul className="list_style_2 clfx">
                    <li>
                        <div className="icon"><img src={big_patch_icon1} alt=""/></div>
                        <div className="txt_area">
                            <h3>API연동으로 자동문자 발송</h3>
                            <p>
                                누구나 쉽게 API연동을 설정할 수 있도록 개발언어별(PHP, ASP)
                                예제파일과 연동매뉴얼을 제공하고 있습니다.
                            </p>

                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={big_patch_icon2} alt=""/></div>
                        <div className="txt_area">
                            <h3>발송결과 보관함에 저장</h3>
                            <p>
                                문자 발송 결과는 발송 후 3개월까지 보관되기 때문에 꼭 필요한 결과 리포트는
                                영구 보관하여 관리할 수 있도록 발송 결과 보관함을 제공합니다. 발송 결과
                                보관함에는 문자 발송 결과를 최대 10개까지 보관할 수 있으며, 보관된 결과를
                                해제할 수도 있습니다.
                            </p>

                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={big_patch_icon3} alt=""/></div>
                        <div className="txt_area">
                            <h3>패치AI를 활용한 주소록 관리</h3>
                            <p>
                                패치AI를 활용한 고객자동고객프로파일링 작업을 통해서 문자 발송을 위한 주소록을 생성하고, 문자발송시 적용 할 수 있습니다.
                            </p>

                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={big_patch_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>발송결과 대시보드</h3>
                            <p>
                                발송 결과에 대한 데이터를 제공합니다. 문자 발송 후 자동 생성되는 결과 리포트
                                에서 발송성공, 실패 및 실패사유를 그래프로 한 눈에 확인하고 PDF, 엑셀로
                                데이터를 다운 받아 관리할 수도 있습니다. 또한 마이페이지 통합리포트에서
                                문자 발송에 대한 전체 현황도 확인할 수 있습니다.
                            </p>

                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={big_patch_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>업종/유형별 문자 보관함 및 샘플제공 </h3>
                            <p>
                                작성한 문자를 보관할 수 있는 문자 저장 기능을 제공합니다. 문자 작성
                                창 하단의 “문자 저장” 버튼 클릭 시 하단의 문자보관함에 문자 내용이
                                저장됩니다. <br/>
                                또한 단문, 장문 발송 유형별 예시 문자를 제공합니다. 사업 유형, 상황별
                                다양한 예시 문자를 참고하여 쉽게 문자를 발송 할 수 있습니다.
                            </p>

                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={big_patch_icon6} alt=""/></div>
                        <div className="txt_area">
                            <h3>발송번호 등록하기</h3>
                            <p>
                                발송 번호 사전 등록제를 시행하고 있습니다. 전기통신사업법 84조에 의거 문자
                                발송시 인증된 발신번호만 발송자로 등록하여 문자를 발송할 수 있도록 하는
                                제도로 ARS, 휴대폰, 통신서비스가입 증명원 서류 제출등의 방법으로 인증 받아
                                최대 10개까지 등록 가능하며 문자 작성시 등록 내역을 선택하여 바로 발송 번호
                                로 사용 할 수 있습니다.
                            </p>

                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={big_patch_icon7} alt=""/></div>
                        <div className="txt_area">
                            <h3>080 수신번호 및 ARS제공</h3>
                            <p>
                                문자 수신자가 수신거부 번호로 전화를 걸면 ARS 안내멘트가 나오고
                                인증번호를 입력하면 수신거부 처리되며 주소록 &gt; 수신거부 관리에서
                                확인할 수 있습니다.
                            </p>

                        </div>
                    </li>
                </ul>
            </div>

            <div className="big_patch_box">
                <h1>080 수신거부번호와 ARS가 필요한 이유!</h1>
                <p>
                    정보통신망법 법령에 따라 영리 목적의 광고성 정보 전송시 (광고)문구 표기 의무 사항준수 및 080 수신 거부 번호를 메시지와 함께 필수적으로 발송해야 하며 미기재 시 벌금 “최대
                    3,000만원 이하의 처벌 대상”입니다.
                </p>
                <div className="btn_area">
                    <a href="#" className="basic-btn01 btn-sky-bd">자세히 보기</a>
                </div>
            </div>
        </section>
    );
};

export default BulkMessage;