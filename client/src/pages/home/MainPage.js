import React, {Fragment, useState,useEffect} from 'react';
import { Link } from 'react-router-dom';
import Slider from "react-slick";
import iconX from '../../asset/image/home/icon_x.png';
import iconO from '../../asset/image/home/icon_o.png';
import backImg from '../../asset/image/back_img.jpg';
import iconTriangle from '../../asset/image/home/icon_triangle.png';
import {getCustomUserCount} from '../../modules/admin/custom_user_count/custom_user_count'
import {useDispatch, useSelector} from "react-redux";
import "../../asset/style/slick/slick.css"
import Helmet from "react-helmet";


const MainPage = () => {

    const dispatch = useDispatch();

    const [showSave, setShowSave] = useState(false);

    const {count,loading} = useSelector(({custom_user_count,loading}) =>({
        count: custom_user_count.count,
        loading: loading['adminCustomUserCount/GET_CUSTOM_USER_COUNT'],
    }))

    useEffect(() => {
        if(loading){
            return;
        }else{
            dispatch(getCustomUserCount());
        }
    },[])

    useEffect(() => {
        return;

    },[count])

    const showChannel = () => {
        document.getElementsByClassName("textLauncherIcon")[0].click();
    }


    let slickSettings = {
        arrows:false,
        autoplay: true,
        dots:false
    };

    return (
        <Fragment>
        <Helmet>
            <title>ENEY 개발자,마켓터데이터분석가가 필요한 사람들을 위한 클라우드 플랫폼</title>
        </Helmet>
        <div className="main_visual">
            <Slider className="slides" {...slickSettings}>
                <div className="list list_01">
                    <div className="txt_area">
                        <h1>에네이클라우드란?</h1>
                        <p>
                            개발자,마켓터, 데이터분석가 필요한 사람들을 위한 클라우드플랫폼
                        </p>
                        <div className="btn_area">
                            <p>자세히 보기</p>
                            <Link to="/customerservice?type=help&page=2&id=385"></Link>
                        </div>
                    </div>
                </div>
                <div className="list list_02">
                    <div className="txt_area">
                        <h1>창업동반자 에네이 클라우드</h1>
                        <p>
                            온라인창업을 위한 비지니스 구축의 모든것<br/>24시간 오픈채팅방 오픈중
                        </p>
                        <div className="btn_area kakao_btn">
                            <a href="https://open.kakao.com/o/gRrMW34b" target="_blank"></a>
                        </div>
                    </div>
                </div>
            </Slider>
        </div>
            <div className="main_container">
                <section className="main_cont_1 main_cont">
                    <ul className="clfx">
                        <li className="icon_1">
                            <h1>마케팅에 최적화된 클라우드</h1>
                            {/*<Link to="/service">이동하기</Link>*/}
                            <p>
                                다양한 마케팅 솔루션을 클라우드 형태로 <br/>
                                사용 할 수 있습니다. 서로다른 솔루션에서 발생하는 <br/>
                                데이터들을 한곳에서 수집 할 수 있습니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=395">더 보기</Link>
                            </div>
                        </li>
                        <li className="icon_2">
                            <h1>참여형클라우드</h1>
                            <p>
                                참여형 클라우드 플랫폼으로, 클라우드 기반의 공유경제 <br/>
                                플랫폼을 지향합니다. 다양한 많은 업체들이 참여하여 <br/>
                                에네이 클라우드를 만들어 갑니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=396">더 보기</Link>
                            </div>
                        </li>
                        <li className="icon_3">
                            <h1>클라우드 인티그레이션</h1>
                            <p>
                                시스템 구조를 진단하고<br/>
                                에네이 클라우드 전환 로드맵을 수립해<br/>
                                클라우드 서비스를 조합하고 설계 할 수 있습니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=397">더 보기</Link>
                            </div>
                        </li>
                    </ul>
                </section>

                <section className="main_cont_2" style={{backgroundImage:`url(${backImg})`,color:"red"}}>
                    <div className="main_cont">
                        <h1>에네이 클라우드를 이용하여 비지니스 플랫폼을<br/>강화시키고 성장시켜 보세요!</h1>
                        <p>온라인 비지니스 전문고객팀과 마케팅 전문가 커뮤니티를 통해 <br/>
                        온라인 비지니스의 방법론을 익히고 도구를 최대한 활용해 보세요!
                        </p>
                        <ul className="clfx">
                            <li>
                                <span><img src={require('../../asset/image/company_icon.png')} alt="company_icon"/></span>
                                <strong className="number">{count.corp}<span className="plus">+</span></strong>
                                <span>기업고객 수</span>
                            </li>
                            <li>
                                <span className="bold" style={{lineHeight:2.5}}><img src={require('../../asset/image/blogs.png')} alt="blogs"/></span>
                                <strong className="number">{count.visit}<span className="plus">+</span></strong>
                                <span>월간 방문</span>
                            </li>
                            <li>
                                <span className="bold" style={{lineHeight:2.5}}><img src={require('../../asset/image/api_connect.png')} alt="api_connect"/></span>
                                <strong className="number">{count.api}K</strong>
                                <span>&nbsp;</span>
                            </li>
                            <li>
                                <span className="bold" style={{lineHeight:2.5}}><img src={require('../../asset/image/inboundcall.png')} alt="inboundcall"/></span>
                                <strong className="number">{count.total.toString().substring(0,2)}M<span className="plus">+</span></strong>
                                <span>누적콜</span>
                            </li>
                        </ul>
                    </div>
                </section>

                <section className="main_cont5">
                    <div className="main_cont">
                        <div className="box_01">
                            <strong>{count.corp}<span className="plus">+</span></strong>
                            <p>에네이 클라우드로</p>
                            <p>비지니스를 성장시키는 기업</p>
                        </div>
                        <div className="box_02">
                            <ul className="clfx">
                                <li>
                                    <a><img src={require("../../asset/image/drive_banner.png")} alt="drive_banner" /></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/hasung_banner.png")} alt="hasung_banner"/></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/soongsil_banner.png")} alt="soongsil_banner"/></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/sejong_banner.png")} alt="sejong_banner" /></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/inpro_banner.png")} alt="inpro_banner"/></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/kait_banner.png")} alt="kait_banner"/></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/woozoo_banner.png")} alt="woozoo_banner"/></a>
                                </li>
                                <li>
                                    <a><img src={require("../../asset/image/_banner.png")} alt="_banner"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </section>

                <section className="main_cont_3">
                    <div className="main_cont">
                    <h1 className="title_style_1">서비스 릴리스 정책</h1>
                    <p className="txt_style_1">에네이 서비스는 아래와 같은 릴리스 정책에 따라 관리합니다.</p>
                    <ul className="clfx">
                        <li className="bg_1">
                            <span className="title">ALPHA</span>
                            <p>
                                정식 서비스 출시 전, 사용자에게 공개하는 서비스입니다. <br/>
                                알파 서비스는 회사의 정책에 따라 <br/>
                                지원하지 않을(Fade-out) 수 있습니다.
                            </p>
                        </li>
                        <li className="bg_2">
                            <span className="title">BETA</span>
                            <p>
                                정식 서비스 출시 전, 사용자에게 공개하는 서비스입니다. <br/>
                                서비스명에 Beta로 표시합니다.
                            </p>
                        </li>
                        <li className="bg_3">
                            <span className="title">GA</span>
                            <p>
                                정식 서비스입니다.
                            </p>
                        </li>
                    </ul>
                    <table>
                        <colgroup>
                            <col style={{width:"25%"}}/>
                            <col style={{width:"25%"}}/>
                            <col style={{width:"25%"}}/>
                            <col style={{width:"25%"}}/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">구분</th>
                            <th scope="col">SLA</th>
                            <th scope="col">서비스 지속</th>
                            <th scope="col">기간</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td className="color_1">ALPHA</td>
                            <td><img src={iconX} alt="x"/></td>
                            <td><img src={iconTriangle} alt=""/></td>
                            <td>3개월</td>
                        </tr>
                        <tr>
                            <td className="color_2">BETA</td>
                            <td><img src={iconX} alt="x"/></td>
                            <td><img src={iconO} alt="o"/></td>
                            <td>3개월</td>
                        </tr>
                        <tr>
                            <td className="color_3">GA</td>
                            <td><img src={iconO} alt="o"/></td>
                            <td><img src={iconO} alt="o"/></td>
                            <td>-</td>
                        </tr>
                        </tbody>
                    </table>
                    <p className="txt_1">ALPHA, BETA 기간은 연장 혹은 단축될 수 있습니다.</p>
                    </div>
                </section>

                <section className="main_cont_4">
                    <div className="main_cont">
                        <h1 className="title_style_1">고객 문의하기</h1>
                        <ul className="clfx">
                            <li className="online_inquiry">
                                <h2>온라인 1:1 문의</h2>
                                <p>
                                    문의 사항이 있으시면 언제든지 물어보세요 <br/>
                                    친절하게 응대해드리겠습니다.
                                </p>
                                <div className="btn_area">
                                    <a  onClick={() => showChannel()}>문의하기</a>
                                </div>
                            </li>
                            <li>
                                <h2>대표전화</h2>
                                <div className="tel">050-6191-0024</div>
                                <p>
                                    운영시간 : 월~ 금 09:30 ~ 18:00<br/>
                                    고객 센터 운영 시간 종료 후 문의사항은 1:1문의로
                                </p>
                            </li>
                            <li>
                                <h2>기술문의</h2>
                                <div className="tel">050-6191-0025</div>
                                <p>
                                    대표 이메일 sales@eney.co.kr로 주시면 <br/>
                                    신속히 응대하겠습니다.
                                </p>
                                {/*<Link to="/service">이동하기</Link>*/}
                            </li>
                        </ul>
                    </div>
                </section>
            </div>

        </Fragment>
    );
};

export default React.memo(MainPage);