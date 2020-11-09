import React,{Fragment} from 'react';
import CompanyIntroduceTab from "./CompanyIntroduceTab";

const CompanyHistory = ({page}) => {
    return (
        <Fragment>

            <section className="main_cont">
                <h1 className="title_style_5">회사연혁</h1>
                <p className="txt_style_1">발전이 기대되는 기업, 에네이의 발자취입니다.</p>
            </section>

            <CompanyIntroduceTab page={page}/>

            <div className="company_history">
                <div className="company_logo">
                    <img src={require('../../asset/image/introduce/history_logo.jpg')}alt="" className="logo"/>
                </div>
                <ul className="company_history_list clfx">
                    <li className="left">
                        <div className="box">
                            <div className="circle"><img src={require('../../asset/image/introduce/history_li.gif')}alt=""/></div>
                            <div className="arrow web"><img src={require('../../asset/image/introduce/history_left_arrow.gif')}alt=""/>
                            </div>
                            <div className="arrow mb"><img src={require('../../asset/image/introduce/history_right_arrow.gif')} alt=""/>
                            </div>
                            <div className="year">2014</div>
                            <ul>
                                <li>
                                    <div className="date">2014. 11</div>
                                    <div className="txt">에네이 창업</div>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li className="right">
                        <div className="box">
                            <div className="circle"><img src={require('../../asset/image/introduce/history_li.gif')} alt=""/></div>
                            <div className="arrow"><img src={require('../../asset/image/introduce/history_right_arrow.gif')}alt=""/></div>
                            <div className="year">2015</div>
                            <ul>
                                <li>
                                    <div className="date">2015. 02</div>
                                    <div className="txt">숭실대학교 창업보육세터 입주</div>
                                </li>
                                <li>
                                    <div className="date">2015. 03</div>
                                    <div className="txt">별정통신사업자 2호 취득</div>
                                </li>
                                <li>
                                    <div className="date">2015. 04</div>
                                    <div className="txt"> SK브로드밴드 특수부가사업 파트너 계약 체결</div>
                                </li>
                                <li>
                                    <div className="date">2015. 07</div>
                                    <div className="txt">패치콜(PatchCall) 서비스 런칭</div>
                                </li>
                                <li>
                                    <div className="date">2015. 11</div>
                                    <div className="txt">겔럭시아컴즈 영업 협약 체결</div>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li className="left">
                        <div className="box">
                            <div className="circle"><img src={require('../../asset/image/introduce/history_li.gif')}alt=""/></div>
                            <div className="arrow web"><img src={require('../../asset/image/introduce/history_left_arrow.gif')} alt=""/>
                            </div>
                            <div className="arrow mb"><img src={require('../../asset/image/introduce/history_right_arrow.gif')} alt=""/>
                            </div>
                            <div className="year">2016</div>
                            <ul>
                                <li>
                                    <div className="date">2016. 07</div>
                                    <div className="txt">창업선도대학 아이템 사업화 지원사업 선정</div>
                                </li>
                                <li>
                                    <div className="date">2016. 07</div>
                                    <div className="txt">세종텔레콤 콜데이터 관련 업무 협약 체결</div>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li className="right">
                        <div className="box">
                            <div className="circle"><img src={require('../../asset/image/introduce/history_li.gif')} alt=""/></div>
                            <div className="arrow"><img src={require('../../asset/image/introduce/history_right_arrow.gif')} alt=""/></div>
                            <div className="year">2017</div>
                            <ul>
                                <li>
                                    <div className="date">2017. 08</div>
                                    <div className="txt">SBA 프로그램 4차 진행</div>
                                </li>
                                <li>
                                    <div className="date">2017. 09</div>
                                    <div className="txt">창업선도대학 후속 지원 사업 선정</div>
                                </li>
                                <li>
                                    <div className="date">2017. 11</div>
                                    <div className="txt">특수한 유형의 부가통신사업자 등록</div>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>

        </Fragment>
    );
};

export default CompanyHistory;