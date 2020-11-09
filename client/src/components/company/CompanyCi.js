import React,{Fragment} from 'react';
import CompanyIntroduceTab from "./CompanyIntroduceTab";

const CompanyCi = ({page}) => {
    return (
        <Fragment>
            <section className="main_cont">
                <h1 className="title_style_5">CI/BI소개</h1>
                <p className="txt_style_1">에네이의 CI를 소개합니다.</p>
            </section>

            <CompanyIntroduceTab page={page}/>

            <h2 className="title_style_3"><span>CI소개</span></h2>
            <div className="ci_wrap">
                <div className="ci_big_area">
                    <div className="img_area"><img src={require('../../asset/image/introduce/ci_img_1.jpg')}  alt=""/></div>
                    <div className="txt_area">
                        <p className="txt_1">
                            <strong>“에네이는 하늘색을 좋아합니다”</strong>
                            에네이의 CI는 구름모양의 형상은 클라우드 기반의 통신(ICT) 서비스를 제공하는 정체성을 담고 있습니다.<br/>
                            eney는 Everything And Every one for You라는 슬로건으로 글로벌하게 고객 중심 환경을 만들어가고 있는 서비스 기업입니다.
                        </p>
                        <div className="btn_area">
                            <a href="#" className="btn_download"><span>AI Download</span></a>
                            <a href="#" className="btn_download"><span>JPG Download</span></a>
                            <a href="#" className="btn_download"><span>PNG Download</span></a>
                        </div>
                    </div>
                </div>

                <div className="small_ci">
                    <ul className="clfx">
                        <li>
                            <span className="title">기본형</span>
                            <div className="img_area"><img src={require('../../asset/image/introduce/ci_img_2.jpg')} alt=""/></div>
                        </li>
                        <li>
                            <span className="title">슬로건형</span>
                            <div className="img_area"><img src={require('../../asset/image/introduce/ci_img_3.jpg')} alt=""/></div>
                        </li>
                    </ul>
                </div>

                <div className="ci_color">
                    <h3>CI Color</h3>
                    <ul className="clfx">
                        <li className="bg_sky">
                            <div className="inner">
                                main skyblue<br/>
                                C67 M13<br/>
                                PANTONE 298C<br/>
                                #37afe5
                            </div>
                        </li>
                        <li className="bg_gray">
                            <div className="inner">
                                slogan gray<br/>
                                C66 M57 Y54 K4<br/>
                                PANTONE 424C<br/>
                                #6c6f70
                            </div>
                        </li>
                    </ul>
                </div>
            </div>

            <h2 className="title_style_3"><span>BI소개</span></h2>

            <div className="bi_wrap">
                <div className="ci_big_area">
                    <div className="img_area"><img src={require('../../asset/image/introduce/bi_img_1.jpg')} alt=""/></div>
                    <div className="txt_area">
                        <p className="txt_1">
                            <strong>“어느 번호에도 붙일 수 있는 패치콜!”</strong>
                            패치콜의 ‘#’은 전화기에 #버튼을 연상시켜 누가 보아도 전화 심벌을 느낄 수 있도록 기획됐으며, 패치(Patch)라는 의미는 어떠한 번호에도 붙여 전화문의 데이터를
                            수집할 수 있다는 의미입니다.<br/>
                            따라서 패치콜의 ‘#’은 붙일 수 있는 반창고 심벌을 연상시킵니다
                        </p>
                        <div className="btn_area">
                            <a href="#" className="btn_download"><span>AI Download</span></a>
                            <a href="#" className="btn_download"><span>JPG Download</span></a>
                            <a href="#" className="btn_download"><span>PNG Download</span></a>
                        </div>
                    </div>
                </div>

                <div className="small_ci small_bi">
                    <ul className="clfx">
                        <li>
                            <span className="title">한글형</span>
                            <div className="img_area">
                                <img src={require('../../asset/image/introduce/bi_img_2.jpg')}alt=""/>
                            </div>
                        </li>
                        <li>
                            <span className="title">영문형</span>
                            <div className="img_area">
                                <img src={require('../../asset/image/introduce/bi_img_3.jpg')}alt=""/>
                            </div>
                        </li>
                        <li>
                            <span className="title">심볼형</span>
                            <div className="img_area">
                                <img src={require('../../asset/image/introduce/bi_img_4.jpg')} alt=""/>
                            </div>
                        </li>
                    </ul>
                </div>

                <div className="ci_color mb_20">
                    <h3>한글형 Color</h3>
                    <ul className="clfx">
                        <li className="bg_sky">
                            <div className="inner">
                                main skyblue<br/>
                                C67 M13<br/>
                                PANTONE 298C<br/>
                                #37afe5
                            </div>
                        </li>
                        <li className="bg_purple">
                            <div className="inner">
                                pupple<br/>
                                C52 M100 Y26<br/>
                                PANTONE 2425C<br/>
                                #6c6f70
                            </div>
                        </li>
                        <li className="bg_blue">
                            <div className="inner">
                                blue-green<br/>
                                C91 M64 Y41 K2<br/>
                                PANTONE 7462C<br/>
                                #0b5a79
                            </div>
                        </li>
                    </ul>
                </div>

                <div className="ci_color bi_color clfx">
                    <div>
                        <h3>영문형 Color</h3>
                        <ul className="clfx">
                            <li className="bg_sky">
                                <div className="inner">
                                    main skyblue<br/>
                                    C67 M13<br/>
                                    PANTONE 298C<br/>
                                    #37afe5
                                </div>
                            </li>
                            <li className="bg_lightgray">
                                <div className="inner">
                                    gray<br/>
                                    C45 M37 Y35<br/>
                                    PANTONE Cool Gray 7 C<br/>
                                    #9b9b9b
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <h3>심볼형 Color</h3>
                        <ul className="clfx">
                            <li className="bg_sky">
                                <div className="inner">
                                    main skyblue<br/>
                                    C67 M13<br/>
                                    PANTONE 298C<br/>
                                    #37afe5
                                </div>
                            </li>
                            <li className="bg_darkgray">
                                <div className="inner">
                                    symbol gray<br/>
                                    C73 M65 Y63 K19<br/>
                                    PANTONE 425C<br/>
                                    #505252
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <h2 className="title_style_3"><span>색상규정</span></h2>
            <div className="color_rule">
                <p className="txt_1">에네이 로고는 흰색 배경일때에만 기존 메인 색상을 사용하며, 그 외에모든 색상은 흰색으로 사용합니다. </p>
                <h3>올바른 예</h3>
                <ul className="clfx">
                    <li><img src={require('../../asset/image/introduce/bi_color_1.jpg')} alt=""/></li>
                    <li><img src={require('../../asset/image/introduce/bi_color_2.jpg')} alt=""/></li>
                    <li><img src={require('../../asset/image/introduce/bi_color_3.jpg')} alt=""/></li>
                    <li><img src={require('../../asset/image/introduce/bi_color_4.jpg')} alt=""/></li>
                </ul>

                <h3>잘못된 예</h3>
                <ul className="clfx">
                    <li><img src={require('../../asset/image/introduce/bi_color_5.jpg')} alt=""/></li>
                    <li><img src={require('../../asset/image/introduce/bi_color_6.jpg')} alt=""/></li>
                    <li><img src={require('../../asset/image/introduce/bi_color_7.jpg')} alt=""/></li>
                    <li><img src={require('../../asset/image/introduce/bi_color_8.jpg')} alt=""/></li>
                </ul>
            </div>
        </Fragment>
    );
};

export default CompanyCi;