import React from 'react';
import {Link} from 'react-router-dom';
import patch_ai_img from '../../asset/image/patch_ai_img.jpg'
import btn_app from '../../asset/image/btn_app.jpg'
import btn_google from '../../asset/image/btn_google.jpg'
import patch_ai_icon1 from '../../asset/image/patch_ai_icon1.jpg'
import patch_ai_icon2 from '../../asset/image/patch_ai_icon2.jpg'
import patch_ai_icon3 from '../../asset/image/patch_ai_icon3.jpg'
import patch_ai_icon4 from '../../asset/image/patch_ai_icon4.jpg'
import patch_ai_icon5 from '../../asset/image/patch_ai_icon5.jpg'
import patch_call_icon4 from "../../asset/image/patch_call_icon4.jpg";
import patch_call_icon5 from "../../asset/image/patch_call_icon5.jpg";
import patch_call_icon1 from "../../asset/image/patch_call_icon1.jpg";

const PatchAi = () => {
    return (
        <section className="sub_container">
            <div className="navi clfx">
                <ul className="clfx">
                    <li>Home</li>
                    <li>서비스</li>
                    <li>패치AI</li>
                </ul>
            </div>

            <div className="sub_title_area clfx">
                <div className="img_area"><img src={patch_ai_img} alt=""/></div>
                <div className="txt_area">
                    <h1 className="title_style_2">패치 AI 컨택센터</h1>
                    <p className="txt_style_2">
                        에네이의 빅데이터기반 인공지능 엔진을 활용한 컨택센터 클라우드 입니다. 가상번호를 활용해서 <br/>
                        녹취, 컬러링, 콜백메세징 다양한 콜센터 주요기능 제공 및 자동고객프로파일링 기능을 통한 고객관리가 가능합니다.<br/> 수집된 고객데이터를 활용하여 온라인타겟광고 및 다이렉트마케팅(문자, 알림톡, 이메일)영역 까지 확대가 가능합니다.
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
                        <h3>1. 마케터없이 마케팅을 하고싶은 사업자</h3>
                        <p>전화문의 및 웹,채팅로봇 등에서 수집되는 합법적인 방법으로 수집된 정보를 자동으로 데이터를 생성해 디지털마케팅을 위한 지표로 활용됩니다.</p>
                    </li>
                    <li>
                        <h3>2. 신규사업을 하면서 고객정보가 없는 사업자</h3>
                        <p>
                            고객정보가 없어도 업종 및 유형별 맞춤형 타겟광고를 통한 매출전환율을 높일 수 있습니다.
                        </p>
                    </li>
                    <li>
                        <h3>3. 다이렉트마케팅을 효율적으로 하고싶은 사업자</h3>
                        <p>개인화url분석 문자/알림톡 마케팅의 효율성을 높여 문자/알림톡 비용을 줄일 수 있습니다.</p>
                    </li>
                </ul>
            </div>

            <div className="function_list bd_none">
                <h2 className="title_style_3">주요기능</h2>
                <ul className="list_style_2 clfx">
                    <li>
                        <div className="icon"><img src={patch_ai_icon1} alt=""/></div>
                        <div className="txt_area">
                            <h3>자동고객 프로파일링</h3>
                            <p>
                                패치콜 플랫폼으로 유입되는 콜로그 기준으로 자동고객 리스트를 <br/>
                                생성하여 타겟광고 및 다이렉트 마케팅을 위한 데이터로 활용됩니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=4&id=414">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_ai_icon2} alt=""/></div>
                        <div className="txt_area">
                            <h3>고객 프로파일링 강화</h3>
                            <p>
                                고객데이터가 없어도, 업종 및 유형에 따라 타겟광고 할 수 있으며,
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=4&id=415">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon4} alt=""/></div>
                        <div className="txt_area">
                            <h3>녹취</h3>
                            <p>
                                신청한 번호 별 전수녹취 <br/>
                                녹취파일은 웹 콜로그 조회에서 확인 가능
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=348">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_ai_icon3} alt=""/></div>
                        <div className="txt_area">
                            <h3>다이렉트마케팅(대량문자/알림톡)</h3>
                            <p>
                                대량문자 및 알림톡 서비스 입니다. 경쟁사 대비 차별화 포인트는 다이렉트 <br/>
                                마케팅의 발송시 개인화url를 활용한 효과분석서비스를 제공합니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=4&id=416">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_ai_icon4} alt=""/></div>
                        <div className="txt_area">
                            <h3>자동타겟광고(페이스북/인스타그램) </h3>
                            <p>
                                고객프로파일링 기반 자동타겟광고 서비스 제공 One Click 으로 <br/>
                                타겟광고를 할 수 있습니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=4&id=417">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>CALL BACK 메세징</h3>
                            <p>
                                전화 부재중 또는 통화종료 후 발신자에게 이벤트, 홍보관련 메시지 전송 <br/>
                                SMS / LMS / MMS 모두 확인 가능
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=350">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon1} alt=""/></div>
                        <div className="txt_area">
                            <h3>컬러링 / 착신멘트 / TTS엔진</h3>
                            <p>
                                컬러링 : 귀사의 컬러링을 모든 가상번호에 적용 사용자의 충성도 증대<br/>
                                착신멘트 : 콜 유입시 귀사의 가맹점에 짧은 음원을 들려줌<br/>
                                TTS엔진: 번호별 DB에 등록된 발신지명을 기계음으로 들려줌
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=390">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    {/*<li>
                        <div className="icon"><img src={patch_ai_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>고객별 개인화url를 통한 유입분석 </h3>
                            <p>
                                문자발송시 개인화url를 통해 목적지 페이지 <br/>
                                데이터 수집 분석이 가능 합니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=4&id=418">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>*/}
                </ul>
            </div>

            {/*<div className="patch_app_area">
                <h2 className="title_style_3">패치콜AI 앱</h2>
                <div className="txt_area">
                    <p>
                        웹과 앱에서 합리적인 고객관리를 할 수 있습니다. <br/>
                        마케터가 없는 중소기업을 위한 인공지능 솔루션
                        <span>Customer Profiling Data! More Effective Marketing! </span>
                    </p>
                    <div className="btn_area">
                        <a href="#" className="basic-btn01 btn-sky-bg">HOW IT WORKS</a>
                    </div>
                    <div className="store_btn">
                        <a href="#"><img src={btn_google} alt="google pay"/></a>
                        <a href="#"><img src={btn_app} alt="app store"/></a>
                    </div>
                </div>
            </div>*/}
        </section>
    );
};

export default PatchAi;