import React,{Fragment} from 'react';
import CompanyIntroduceTab from "./CompanyIntroduceTab";
import {Link} from "react-router-dom";


const CompanyIntroduce = ({page}) => {
    return (
        <Fragment>

            <section className="main_cont">
                <h1 className="title_style_5">회사소개</h1>
                <p className="txt_style_1">숫자로 보는 마케팅 인사이트, 에네이를 소개합니다.</p>
            </section>

            <CompanyIntroduceTab page={page}/>

            <div className="company_intro">
                <h2 className="title_style_3"><span>사업영역</span></h2>
                <p className="txt_1">
                    <span className="f-blue">O2O</span> 사업자들을 위한<br/>ICT 기반의 스마트한 <strong>마케팅 솔루션 서비스</strong>를 제공
                </p>

                <div className="txt_c"><img src={require('../../asset/image/introduce/company_intro_img.jpg')} alt=""/></div>
                <p className="txt_2">
                    에네이는 2014년도에 3억 원의 자본금으로 창업하여 현재 지속 성장하는 기업으로, 구성원들이 일속에서 자아실현을 할 수 있는 곳을 만들고자 합니다.<br/>
                    2014년 회사를 설립한 이래로 기업과 고객들을 연결하기 위해 통신, 웹, 소셜 영역에서 에네이만의 고유한 기술을 개발하였고,<br className="mb"/>
                    3년 동안 90개의 고객사 유치 100% 고객만족과 같은 성과를 달성하였습니다.<br/>
                    <strong>혁신적이고 창의적인 기업으로 여러분의 비즈니스 파트너가 되겠습니다.</strong>
                </p>

                <h2 className="title_style_3"><span>목표가치 창출</span></h2>

                <div className="intro_value">
                    <div className="img_area">
                        <img src={require('../../asset/image/introduce/comapny_intro.jpg')} alt=""/>
                    </div>
                    <ul>
                        <li className="value_1">
                            <strong>새로운 가치 창출</strong>
                            국내 통신, 금융 산업은 기성 가치 개혁이 필요합니다.<br/>
                            사용자들의 Needs를 더 적극적으로 반영,<br/>
                            접근성이 쉽게 이용되야 합니다.
                        </li>
                        <li className="value_2">
                            <strong>적극적 고용 창출</strong>
                            한국장학재단, 고용노동부와 연계하여<br/>
                            매년 학생 및 청년 인턴십을 진행하고있습니다.<br/>
                            2015년 한국장학재단 우수기업 사례로 선정되었습니다.
                        </li>
                        <li className="value_3">
                            <strong>매년 능동적 성장</strong>
                            매출, 영업이익, 당기순이익을 전년대비<br/>
                            2배 성장률 목표로 하고 있습니다.<br/>
                            VC 투자유치 및 신규 VR 서비스 R&D 개발을<br/>
                            계획하고 있습니다.
                        </li>
                        <li className="value_4">
                            <strong>안정적 서비스 제공</strong>
                            안정적인 서비스 제공을 위한<br/>
                            SLA(Service Level Agreement)를 적용하여<br/>
                            고객의 충성도 증대를 목표로 합니다.
                        </li>
                    </ul>
                </div>

                <h2 className="title_style_3"><span>회사개요</span></h2>
                <div className="tb_style_5">
                    <table>
                        <colgroup>
                            <col style={{width:"12.7%"}}/>
                            <col style={{width:"37.3%"}}/>
                            <col style={{width:"12.7%"}}/>
                            <col style={{width:"37.3%"}}/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row" className="bd_no">회사명</th>
                            <td>주식회사 에네이</td>
                            <th scope="row">설립일</th>
                            <td>2014년 11월 1일</td>
                        </tr>
                        <tr>
                            <th scope="row" className="bd_no">대표이사</th>
                            <td>전재혁</td>
                            <th scope="row">대표번호</th>
                            <td>0506-191-0024 / FAX 070-7815-2201</td>
                        </tr>
                        <tr>
                            <th scope="row" className="bd_no">주소</th>
                            <td>서울특별시 동작구 상도로 369 (상도동) 창신관 212호</td>
                            <th scope="row">사업분야</th>
                            <td>ICT, 빅데이터, 콜인텔리전스 전문 기업</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </Fragment>
    );
};

export default CompanyIntroduce;