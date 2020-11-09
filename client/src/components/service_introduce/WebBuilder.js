import React,{Fragment,useState,useEffect} from 'react';
import {Link} from 'react-router-dom'

const WebBuilder = () => {

    const [selectItem,setSelectItem] = useState(1);

    const showChannel = () => {
        document.getElementsByClassName("textLauncherIcon")[0].click();
    }


    return (
        <Fragment>
            <div className="sub_webService_visual">
                <div className="sub_txt">
                    <div className="inner">
                        <h1>템플릿만? No! 직접 만드는 쇼핑몰도 No! </h1>
                        <p>
                            전문 디자이너가 처음부터 끝까지 고객님은 선택만 <br/>기획부터 마케팅까지 Onestop 서비스
                        </p>
                    </div>
                </div>
            </div>

            <section className="sub_container web_service">
                <div className="intro_area">
                    <h2 className="title_1">모바일/웹서비스 개발/운영</h2>
                    <p>
                        템플릿을 제공만하는게 아닌 직접 기획부터 디자인까지 원스탑으로 제작해 드리는 서비스입니다.<br/>
                        단순한 가맹점을 연결하는 플랫폼 부터, IOT기반의 O2O플랫폼까지 아이디어를 사업으로 만들어 드릴 수있도록 기획 되어 졌습니다.
                    </p>
                    <div className="btn_area">
                        <Link to="/price" className="basic-btn01 btn-sky-bg"><span>서비스 이용하기</span></Link>
                    </div>
                </div>

                <div className="service_advantage">
                    <h2>서비스 장점</h2>
                    <ul className="clfx">
                        <li>
                            <div className="img_area"><img src={require("../../asset/image/service_introduce/web_service_img_1.jpg")} alt=""/></div>
                            <div className="txt_area">
                                <span className="number">01</span>
                                정형화된 템플릿에 자산의 로고 및 색상 변경을 전문 웹디자이너를 통해 적용하여, 합리적인 비용으로 자사몰을 구축 할 수 있습니다.
                            </div>
                        </li>
                        <li>
                            <div className="img_area"><img src={require("../../asset/image/service_introduce/web_service_img_2.jpg")} alt=""/></div>
                            <div className="txt_area">
                                <span className="number">02</span>
                                디자인 설계부분에서 약손된 jpg로 사이트의 구성을 확인 할 수 있습니다.
                            </div>
                        </li>
                    </ul>
                </div>
            </section>

            <div className="production_process">
                <div className="inner clfx">
                    <div className="left_txt">
                        <h2>제작과정</h2>
                        <p>
                            사이트가 만들어지는 전반적인 과정을 <br/>동영상으로 확인하실수 있습니다.
                        </p>
                    </div>
                    <div className="video_area">
                        <iframe src="https://player.vimeo.com/video/395084301?title=0&byline=0&portrait=0" width="640"
                                height="360" frameBorder="0" allow="autoplay; fullscreen" allowFullScreen></iframe>
                    </div>
                </div>
            </div>

            <section className="sub_container web_service">
                <div className="production_case">
                    <h2>추천제작사례</h2>
                    <div className="tab_style_1">
                        <ul className="clfx">
                            <li className={selectItem === 1 && "on"}>
                                <button  onClick={() => setSelectItem(1)}>렌딩페이지</button>
                            </li>
                            <li className={selectItem === 2 && "on"}>
                                <button  onClick={() => setSelectItem(2)}>프렌차이즈 홈페이지</button>
                            </li>
                            <li className={selectItem === 3 && "on"}>
                                <button  onClick={() => setSelectItem(3)}>O2O플랫폼(앱)</button>
                            </li>
                            <li className={selectItem === 4 && "on"}>
                                <button  onClick={() => setSelectItem(4)}>O2O플랫폼(웹)</button>
                            </li>
                            <li className={selectItem === 5 && "on"}>
                                <button  onClick={() => setSelectItem(5)}>쇼핑몰</button>
                            </li>
                        </ul>
                    </div>
                    <ul className="production_case_list clfx" hidden={selectItem !== 1}>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/landding01.png")}alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/landding02.png")} alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/landding03.png")} alt=""/></a></li>
                    </ul>
                    <ul className="production_case_list clfx" hidden={selectItem !== 2}>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/franchise01.png")}alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/franchise02.png")} alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/franchise03.png")} alt=""/></a></li>
                    </ul>
                    <ul className="production_case_list clfx" hidden={selectItem !== 3}>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/o2o_app01.png")}alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/o2o_app02.png")} alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/o2o_app03.png")} alt=""/></a></li>
                    </ul>
                    <ul className="production_case_list clfx" hidden={selectItem !== 4}>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/o2o_web01.png")}alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/o2o_web02.png")} alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/o2o_web03.png")} alt=""/></a></li>
                    </ul>
                    <ul className="production_case_list clfx" hidden={selectItem !== 5}>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/shop01.png")}alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/shop02.png")} alt=""/></a></li>
                        <li><a href="#"><img src={require("../../asset/image/service_introduce/shop03.png")} alt=""/></a></li>
                    </ul>
                </div>

                <div className="key_functions">
                    <h2 className="title_2">주요기능</h2>
                    <div className="clfx">
                        <div className="icon_area">
                            <ul>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key1.png")} alt=""/>
                                    <span>쇼핑</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key2.png")} alt=""/>
                                    <span>회원</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key3.png")} alt=""/>
                                    <span>게시판</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key4.png")} alt=""/>
                                    <span>갤러리</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key5.png")} alt=""/>
                                    <span>전자결제</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key6.png")} alt=""/>
                                    <span>쇼핑검색</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key7.png")} alt=""/>
                                    <span>네이버페이</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key8.png")} alt=""/>
                                    <span>인스타그램연동</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key9.png")} alt=""/>
                                    <span>카카오톡 채널</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key10.png")} alt=""/>
                                    <span>다국어지원</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key11.png")} alt=""/>
                                    <span>구글에널리틱스</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key12.png")} alt=""/>
                                    <span>콜정산</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key13.png")} alt=""/>
                                    <span>패치AI</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key14.png")} alt=""/>
                                    <span>대표번호</span>
                                </li>
                                <li>
                                    <img src={require("../../asset/image/service_introduce/web_service_key15.png")} alt=""/>
                                    <span>컨택센터 연동</span>
                                </li>
                            </ul>
                        </div>

                        <div className="txt_area">
                            <h3>유형별 개발빌더</h3>
                            <p>
                                O2O플랫폼, 쇼핑몰,홈페이지 <br/>
                                사용검증된 플랫폼을 2개월 내 구축 <br/>
                                3rd Part 연동을 통한 플랫폼강화 작업까지
                            </p>
                        </div>
                    </div>
                </div>

                <div className="price_info">
                    <h2 className="title_2">가격 안내</h2>
                    <p>
                        상세견젹은 에네이 프리세일즈 메니저와 자세한 상담을 나눠보세요! <br/>견적금액은 기본적인 금액이며, 추가 개발 부분에 따라서 견적이 변경 됩니다.
                    </p>
                    <div className="tb_style_2">
                        <table>
                            <colgroup>
                                <col style={{width:"23.5%"}}/>
                                <col style={{width:"19.29%"}}/>
                                <col style={{width:"19.29%"}}/>
                                <col style={{width:"18.94%"}}/>
                                <col style={{width:"19.12%"}}/>
                                <col/>
                            </colgroup>
                            <thead>
                            <tr>
                                <th scope="col" colSpan="2">구분</th>
                                <th scope="col">클라이언트</th>
                                <th scope="col">상세</th>
                                <th scope="col" className="sky_bg">제작비용</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>렌딩페이지</td>
                                <td>A안</td>
                                <td>
                                    <ul>
                                        <li>웹</li>
                                        <li>단일 페이지</li>
                                    </ul>
                                </td>
                                <td>
                                    <ul>
                                        <li>그누보드</li>
                                        <li>MY-SQL</li>
                                    </ul>
                                </td>
                                <td>2,000,000원</td>
                            </tr>
                            <tr>
                                <td>프랜차이즈 홈페이지</td>
                                <td>B안</td>
                                <td>
                                    <ul>
                                        <li>웹</li>
                                        <li>관리자</li>
                                        <li>15 페이지</li>
                                    </ul>
                                </td>
                                <td>
                                    <ul>
                                        <li>그누보드</li>
                                        <li>MY-SQL</li>
                                    </ul>
                                </td>
                                <td>5,000,000원</td>
                            </tr>
                            <tr>
                                {/*<td></td>*/}
                                <td>O2O플랫폼(앱)</td>
                                <td>C안</td>
                                <td>
                                    <ul>
                                        <li>안드로이드, IOS</li>
                                        <li>가맹점페이지</li>
                                        <li>별도협의</li>
                                    </ul>
                                </td>
                                <td>
                                    <ul>
                                        <li>REACT</li>
                                        <li>JAVA SPRING</li>
                                        <li>MY-SQL</li>
                                    </ul>
                                </td>
                                <td>25,000,000원</td>
                            </tr>
                            <tr>
                                <td>O2O플랫폼(웹)</td>
                                <td>D안</td>
                                <td>
                                    <ul>
                                        <li>웹</li>
                                        <li>관리자</li>
                                        <li>15페이지</li>
                                    </ul>
                                </td>
                                <td>
                                    <ul>
                                        <li>그누보드</li>
                                        <li>MY-SQL</li>
                                    </ul>
                                </td>
                                <td>5,000,000원</td>
                            </tr>
                            <tr>
                                <td>쇼핑몰</td>
                                <td>E안</td>
                                <td>
                                    <ul>
                                        <li>웹</li>
                                        <li>관리자</li>
                                        <li>15페이지</li>
                                    </ul>
                                </td>
                                <td>
                                    <ul>
                                        <li>영카트, CAFE24</li>
                                        <li>MY-SQL</li>
                                    </ul>
                                </td>
                                <td>5,000,000원</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div className="tel_cs_center clfx">
                    <h2>전화상담</h2>
                    <div className="clfx">
                        <div className="tel_info">
                            <div className="tel"> 0506-191-0024</div>
                            <div className="txt">상담가능시간은 평일 09시00분~18시00분까지 입니다.</div>
                        </div>
                        <div className="right_area clfx">
                            <p>
                                온라인 간편 상담을 원하시면 <br/>온라인 상담 버튼을 눌러주세요!
                            </p>
                            <div className="btn_area">
                                <a onClick={() => showChannel()} className="basic-btn01 btn-sky-bg"><span>온라인 상담문의</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </Fragment>

    );
};

export default WebBuilder;