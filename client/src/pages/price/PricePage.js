import React,{useState} from 'react';
import cx from "classnames";
import {withRouter} from 'react-router-dom'
import icon1 from '../../asset/image/eney_price_icon1.jpg';
import icon2 from '../../asset/image/eney_price_icon2.jpg';
import icon3 from '../../asset/image/eney_price_icon3.jpg';
import icon4 from '../../asset/image/eney_price_icon4.jpg';
import icon5 from '../../asset/image/eney_price_icon5.jpg';
import icon6 from '../../asset/image/eney_price_icon6.jpg';
import icon7 from '../../asset/image/eney_price_icon7.jpg';
import icon8 from '../../asset/image/eney_price_icon8.jpg';
import icon9 from '../../asset/image/eney_price_icon9.jpg';
import icon10 from '../../asset/image/eney_price_icon10.jpg';

const PricePage = ({history}) => {

    const [show, setShow] = useState(0);

    const handleShow = (value) =>{
        setShow(value)
    }

    const showChannel = () => {

        document.getElementsByClassName("textLauncherIcon")[0].click();
    }

    return (
        <section className="sub_container">
            <div className="price_wrap">
                <div className="title_cont">
                    <h1 className="title_style_5">가격 차별화</h1>
                </div>
                <div className="list_1">
                    <ul className="clfx">
                        <li>
                            <div className="icon"><img src={icon1} alt=""/></div>
                            <p>
                                구독료 과금방식을 통한 <br/>고정비 최소화
                            </p>
                        </li>
                        <li>
                            <div className="icon"><img src={icon2} alt=""/></div>
                            <p>
                                일일계산 클라우드 시스템
                            </p>
                        </li>
                        <li>
                            <div className="icon"><img src={icon3} alt=""/></div>
                            <p>
                                보조서비스 무료제공
                            </p>
                        </li>
                    </ul>
                </div>

                <div className="price_cont_1">
                    <p>프리세일즈 담당자 에게 1:1 예상견적을 받아보세요!</p>
                    <div className="btn_area">
                        <a onClick={() => showChannel()}>1:1 비용상담하기</a>
                    </div>
                </div>

                <div className="price_cont_2">
                    <h2>회원가입 EVENT</h2>
                    <p>
                        <strong>가입만 하면 5만원 상당의 포인트를 드립니다!</strong>
                        2019년 12월 01일 이후에 ENEY 신규 회원으로 가입하시는 모든 고객께 <br/>ENEY 서비스에 사용할 수 있는 포인트 50,000원을 드립니다.
                    </p>
                </div>

                <div className="price_step">
                    <ul className="clfx">
                        <li>
                            <div className="icon"><img src={icon4} alt=""/></div>
                            <h2>ENEY에 회원가입</h2>
                            <p>
                                기존에 ENEY회원이 아니라면 <br/>먼저 회원가입을 해주세요!
                            </p>
                            <div className="btn_area">
                                <a href="/auth/user_type">회원가입</a>
                            </div>
                        </li>
                        <li>
                            <div className="icon"><img src={icon5} alt=""/></div>
                            <h2>결제 수단 등록</h2>
                            <p>
                                포인트를 받으시려면 먼저 <br/>결제 수단을 등록해 주세요!
                            </p>
                            <div className="btn_area">
                                <a href="/auth/user_type">결제수단 등록</a>
                            </div>
                        </li>
                        <li>
                            <div className="icon"><img src={icon6} alt=""/></div>
                            <h2>포인트 받기</h2>
                            <p>
                                포인트를 받고 ENEY의 <br/>서비스를 마음껏 이용해 보세요!
                            </p>
                            <div className="btn_area">
                                <a href="/auth/user_type">포인트 받기</a>
                            </div>
                        </li>
                    </ul>
                </div>

                <div className="price_cont_3">
                    <h2>다양한 납부 방법을 지원합니다.</h2>
                    <p className="txt_1">오랫동안 엔에이 서비스를 통해 축적된 IT 운영 경험으로 안정된 서비스를 제공합니다.</p>
                    <ul className="clfx">
                        <li>
                            <div className="icon"><img src={icon7} alt=""/></div>
                            <div className="txt">간편 결제 지원</div>
                        </li>
                        <li>
                            <div className="icon"><img src={icon8} alt=""/></div>
                            <div className="txt">다양한 결제수단 제공</div>
                        </li>
                        <li>
                            <div className="icon"><img src={icon9} alt=""/></div>
                            <div className="txt">법인을 위한 세금계산서 자동 발급</div>
                        </li>
                        <li>
                            <div className="icon"><img src={icon10} alt=""/></div>
                            <div className="txt">포인트를 이용한 결제</div>
                        </li>
                    </ul>
                </div>

                <div className="price_cont_4">
                    <h2>가격 안내</h2>
                    <div className="box">
                        <div className="tab_style_1 tab_title">
                            <ul className="clfx">
                                <li className={cx({on:show === 0})}>
                                    <button onClick={ () => handleShow(0)}>패치콜</button>
                                </li>
                                <li className={cx({on:show === 1})}>
                                    <button onClick={ () => handleShow(1)}>패치Intelligence</button>
                                </li>
                                <li className={cx({on:show === 2})}>
                                    <button onClick={ () => handleShow(2)}>클라우드</button>
                                </li>
                                <li className={cx({on:show === 3})}>
                                    <button onClick={ () => handleShow(3)}>3rd Part</button>
                                </li>
                            </ul>
                        </div>

                        <div className="tab_cont">
                            {show === 0 && (
                                <div className="tb_style_2">
                                    <table>
                                        <colgroup>
                                            <col style={{width:"10%"}}/>
                                            <col style={{width:"12%"}}/>
                                            <col style={{width:"12%"}}/>
                                            <col style={{width:"12%"}}/>
                                            <col style={{width:"12%"}}/>
                                            <col style={{width:"15%"}}/>
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th scope="col" colSpan="3">구분</th>
                                            <th scope="col">과금기준</th>
                                            <th scope="col">공급가액</th>
                                            <th scope="col" className="blue_bg">비고</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td rowSpan="11">패치콜</td>
                                            <td colSpan="2">One</td>
                                            <td>1개</td>
                                            <td>2,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/one')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            {/*<td rowSpan="10">패치콜</td>*/}
                                            <td colSpan="2">Single</td>
                                            <td>10개</td>
                                            <td>10,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/single')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">Double</td>
                                            <td>100개</td>
                                            <td>50,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/double')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">Support</td>
                                            <td>300개</td>
                                            <td>100,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/support')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">Start-up</td>
                                            <td>500개</td>
                                            <td>150,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/start-up')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">Business</td>
                                            <td>1000개</td>
                                            <td>200,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/business')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">Enterprise</td>
                                            <td>2000개</td>
                                            <td>250,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall/enterprise')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">Bigdeal</td>
                                            <td>2000개 이상</td>
                                            <td>별도협의</td>
                                            <td><a href="#" className="btn" onClick={() => alert("고객센터로 문의")}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">녹취</td>
                                            <td>5G</td>
                                            <td>10,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/record/record')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">콜백메세징</td>
                                            <td>Account</td>
                                            <td>10,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/callback_sms/callback')}>신청</a></td>
                                        </tr>
                                        {/*<tr>*/}
                                        {/*    <td colSpan="2">컬러링</td>*/}
                                        {/*    <td>1개</td>*/}
                                        {/*    <td>30,000 원</td>*/}
                                        {/*    <td><a href="#" className="btn" onClick={() => history.push('/service_apply/coloring/coloring')}>신청</a></td>*/}
                                        {/*</tr>*/}
                                        <tr>
                                            <td colSpan="2">패치콜 대시보드</td>
                                            <td>Account</td>
                                            <td>10,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall_dashboard/dashboard')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="2">대표번호</td>
                                            <td colSpan="2">기본료</td>
                                            <td>1개</td>
                                            <td>30,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/general_directory/gen')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">IVR시나리오</td>
                                            <td></td>
                                            <td>Free</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/general_directory/general_directory')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="6">대량문자/알림톡</td>
                                            <td rowSpan="3">카카오톡</td>
                                            <td>알림톡</td>
                                            <td>건당</td>
                                            <td>10원</td>
                                            <td><a href="#" className="btn">신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>친구톡(텍스트)</td>
                                            <td>건당</td>
                                            <td>18원</td>
                                            <td><a href="#" className="btn">신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>친구톡(이미지)</td>
                                            <td>건당</td>
                                            <td>32원</td>
                                            <td><a href="#" className="btn">신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="3">문자</td>
                                            <td>SMS</td>
                                            <td>건당</td>
                                            <td>15원</td>
                                            <td><a href="#" className="btn">신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>LMS</td>
                                            <td>건당</td>
                                            <td>45원</td>
                                            <td><a href="#" className="btn">신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>MMS</td>
                                            <td>건당</td>
                                            <td>140원</td>
                                            <td><a href="#" className="btn">신청</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            )}

                            {show === 1 && (
                                <div className="tb_style_2">
                                    {/*<table>*/}
                                    {/*    /!*<colgroup>*!/*/}
                                    {/*    /!*    <col style="width:20%;"/>*!/*/}
                                    {/*    /!*    <col style="width:20%;"/>*!/*/}
                                    {/*    /!*    <col style="width:20%;"/>*!/*/}
                                    {/*    /!*    <col style="width:20%;"/>*!/*/}
                                    {/*    /!*    <col style="width:20%;"/>*!/*/}
                                    {/*    /!*</colgroup>*!/*/}
                                    {/*    <thead>*/}
                                    {/*    <tr>*/}
                                    {/*        <th scope="col" colSpan="2">구분</th>*/}
                                    {/*        <th scope="col">과금기준</th>*/}
                                    {/*        <th scope="col">공급가액</th>*/}
                                    {/*        <th scope="col" className="blue_bg">비고</th>*/}
                                    {/*    </tr>*/}
                                    {/*    </thead>*/}
                                    {/*    <tbody>*/}
                                    {/*    <tr>*/}
                                    {/*        <td rowSpan="2">패치BI</td>*/}
                                    {/*        <td>O2O인사이트</td>*/}
                                    {/*        <td>1M</td>*/}
                                    {/*        <td>200 원</td>*/}
                                    {/*        <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall_inteligence/patch_bi_o2o')}>신청</a></td>*/}
                                    {/*    </tr>*/}
                                    {/*    <tr>*/}
                                    {/*        <td>쇼핑몰인사이트</td>*/}
                                    {/*        <td>1M</td>*/}
                                    {/*        <td>200 원</td>*/}
                                    {/*        <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall_inteligence/patch_bi_shop')}>신청</a></td>*/}
                                    {/*    </tr>*/}
                                    {/*    </tbody>*/}
                                    {/*</table>*/}
                                    {/*<p className="txt_1">호출데이터 이용량 1G가 무료제공사며, 상기 금액은 1G추가시 발생되는 금액입니다.</p>*/}
                                    <table>
                                        <colgroup>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                        </colgroup>
                                        {/*<colgroup>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*</colgroup>*/}
                                        <thead>
                                        <tr>
                                            <th scope="col" colSpan="2">구분</th>
                                            <th scope="col">과금기준</th>
                                            <th scope="col">공급가액</th>
                                            <th scope="col" className="blue_bg">비고</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td rowSpan="2"><span>패치AI</span> <span style={{"color":"#4288e7"}}>BETA</span></td>
                                            <td>데이터 강화하기</td>
                                            <td>1M</td>
                                            <td>400원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall_inteligence/patch_ai_profile')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>개인화 url</td>
                                            <td>Account</td>
                                            <td>35,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/patchcall_inteligence/patch_ai_private_url')}>신청</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            )}

                            {show === 2 && (
                                <div className="tb_style_2">
                                    <table>
                                        {/*<colgroup>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*    <col style="width:20%;"/>*/}
                                        {/*</colgroup>*/}
                                        <colgroup>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                            <col style={{width:"20%"}}/>
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th scope="col" colSpan="2">구분</th>
                                            <th scope="col">과금기준</th>
                                            <th scope="col">공급가액</th>
                                            <th scope="col" className="blue_bg">비고</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td colSpan="2">웹호스팅</td>
                                            <td>Account</td>
                                            <td>35,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_hosting')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>웹호스팅(영카트)</td>
                                            <td>영카트</td>
                                            <td>Account</td>
                                            <td>50,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_young')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>웹호스팅(그누보드)</td>
                                            <td>그누보드</td>
                                            <td>Account</td>
                                            <td>35,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_gnu')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>웹호스팅(워드프레스)</td>
                                            <td>워드프레스</td>
                                            <td>Account</td>
                                            <td>35,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_word')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="3">SINGLE</td>
                                            <td>VCPU 2</td>
                                            <td rowSpan="3">Account</td>
                                            <td rowSpan="3">100,000 원</td>
                                            <td rowSpan="3"><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_single')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>RAM 4G</td>
                                        </tr>
                                        <tr>
                                            <td>DISK 60G</td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="3">DOUBLE</td>
                                            <td>VCPU 2</td>
                                            <td rowSpan="3">Account</td>
                                            <td rowSpan="3">150,000 원</td>
                                            <td rowSpan="3"><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_double')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>RAM 16G</td>
                                        </tr>
                                        <tr>
                                            <td>DISK 60G</td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">공인고정IP</td>
                                            <td>1개</td>
                                            <td>5,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_fixip')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">이미지 백업</td>
                                            <td>Account</td>
                                            <td>1,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_imgback')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">스냅샷</td>
                                            <td>Account</td>
                                            <td>1,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_snap')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2">웹방화벽(Definder)</td>
                                            <td>Account</td>
                                            <td>110,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/web_definder')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="5">HTTPS<br/>(SSL인증서)</td>
                                            <td>Sectigo Basic(약정 1년)</td>
                                            <td>Account</td>
                                            <td>55,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/sectigo_basic')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>Sectigo Basic Wildcard(약정 1년)</td>
                                            <td>Account</td>
                                            <td>450,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/sectigo_basic_wildcard')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>Sectigo MDC(기본팩/도메인3개)</td>
                                            <td>1Y per each</td>
                                            <td>55,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/sectigo_MDC')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>Symantec Secure Site(약정 1년)</td>
                                            <td>Account</td>
                                            <td>324,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/symantec_secure_site')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>Symantec Secure Site EV(약정 1년)</td>
                                            <td>Account</td>
                                            <td>1,400,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/symantec_secure_site_EV')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="2">VPN</td>
                                            <td>SSL VPN</td>
                                            <td>Account</td>
                                            <td>1,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/ssl_vpn')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>장비기준 VPN(약정 1년)</td>
                                            <td>Account</td>
                                            <td>150,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/ssl_vpn_equip')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td rowSpan="2">방화벽</td>
                                            <td>Forti 80E(Care, 약정 1년)</td>
                                            <td>Account</td>
                                            <td>150,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/definder_80e')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>Forti 100E(Care, 약정 1년)</td>
                                            <td>Account</td>
                                            <td>310,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/definder_100e')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>NMS</td>
                                            <td>PRTG(무료라이센스 100개설치)</td>
                                            <td>Account</td>
                                            <td>500,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/cloud/prtg')}>신청</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            )}

                            {show === 3 && (
                                <div className="tb_style_2">
                                    <table>
                                        {/*<colgroup>*/}
                                        {/*    <col style="width:16.66%;"/>*/}
                                        {/*    <col style="width:16.66%;"/>*/}
                                        {/*    <col style="width:16.66%;"/>*/}
                                        {/*    <col style="width:16.66%;"/>*/}
                                        {/*    <col style="width:16.66%;"/>*/}
                                        {/*    <col/>*/}
                                        {/*</colgroup>*/}
                                        <colgroup>
                                            <col style={{width:"16.66%"}}/>
                                            <col style={{width:"16.66%"}}/>
                                            <col style={{width:"16.66%"}}/>
                                            <col style={{width:"16.66%"}}/>
                                            <col style={{width:"16.66%"}}/>
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th scope="col" colSpan="2" rowSpan="2">구분</th>
                                            <th scope="col" rowSpan="2">과금기준</th>
                                            <th scope="col" colSpan="2">공급가액</th>
                                            <th scope="col" rowSpan="2" className="blue_bg">비고</th>
                                        </tr>
                                        <tr>
                                            <th>설치비</th>
                                            <th>이용료</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td rowSpan="8">운영대행</td>
                                            <td>Googl G.A.</td>
                                            <td>SLA참고</td>
                                            <td>200,000 원</td>
                                            <td>600,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/ga')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>네이버/카카오톡 <br/>검색광고대행</td>
                                            <td>SLA참고</td>
                                            <td>200,000 원</td>
                                            <td>Free</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/search_advertise')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>그누보드</td>
                                            <td>SLA참고</td>
                                            <td>0</td>
                                            <td>200,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/gnu')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>영카트</td>
                                            <td><a href="#" className="" onClick={() => history.push('/customerservice?type=terms&id=411')}>SLA참고</a></td>
                                            <td>0</td>
                                            <td>500,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/youngcart')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>O2O플랫폼</td>
                                            <td><a href="#" className="" onClick={() => history.push('/customerservice?type=terms&id=412')}>SLA참고</a></td>
                                            <td>0</td>
                                            <td>500,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/020')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>웹디자인</td>
                                            <td><a href="#" className="" onClick={() => history.push('/customerservice?type=terms&id=413')}>SLA참고</a></td>
                                            <td>0</td>
                                            <td>500,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/web_design')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>에네이클라우드</td>
                                            <td>SLA참고</td>
                                            <td>0</td>
                                            <td>1,000,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/eney_cloud')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>SEO최적화</td>
                                            <td>SLA참고</td>
                                            <td>500,000 원</td>
                                            <td>Free</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/sed')}>신청</a></td>
                                        </tr>
                                        <tr>
                                            <td>교육/컨설팅 <br/>아카데미</td>
                                            <td>교육/컨설팅</td>
                                            <td>account</td>
                                            <td colSpan="2">100,000 원</td>
                                            <td><a href="#" className="btn" onClick={() => history.push('/service_apply/3rdpart/consulting')}>신청</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            )}



                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default withRouter(PricePage);