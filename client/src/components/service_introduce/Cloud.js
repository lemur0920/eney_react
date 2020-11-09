import React from 'react';

import cloud_img from '../../asset/image/cloud_img.jpg'
import cloud_icon1 from '../../asset/image/cloud_icon1.jpg'
import cloud_icon2 from '../../asset/image/cloud_icon2.jpg'
import cloud_icon3 from '../../asset/image/cloud_icon3.jpg'
import cloud_icon4 from '../../asset/image/cloud_icon4.jpg'
import cloud_icon5 from '../../asset/image/cloud_icon5.jpg'
import cloud_icon6 from '../../asset/image/cloud_icon6.jpg'
import cloud_icon7 from '../../asset/image/cloud_icon7.jpg'
import cloud_icon8 from '../../asset/image/cloud_icon8.jpg'
import {Link} from 'react-router-dom'

const Cloud = () => {

    const hasWindow = (typeof window !== 'undefined') ? true : false;

    if(hasWindow){
        window.scrollTo(0, 0);
    }


    return (
        <section className="sub_container">
            <div className="navi clfx">
                <ul className="clfx">
                    <li>Home</li>
                    <li>서비스</li>
                    <li>ENEY 클라우드</li>
                </ul>
            </div>

            <div className="sub_title_area clfx">
                <div className="img_area"><img src={cloud_img} alt=""/></div>
                <div className="txt_area">
                    <h1 className="title_style_2">ENEY 클라우드</h1>
                    <p className="txt_style_2">
                        에네이의 마케팅솔루션에 최적화 된 클라우드 서비스를 제공합니다.
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
                        <h3>1. AWS, 구글 클라우드를 사용하고 싶지만, 관리인력이 없어 이용을 못하는 고객</h3>
                    </li>
                    <li>
                        <h3>2. 에네이의 모바일/웹서비스 개발/운영을 이용하고 싶은 고객</h3>
                        <p>
                            에네이의 모바일/웹서비스 개발 및 운영을 의뢰하고 싶다면 AWS, 구글 클라우드를 사용하는 것 보다 자신의 서비스에 최적화된 합리적인 비용으로 이용이 가능 합니다.
                        </p>
                    </li>
                    <li>
                        <h3>3. 에네이의 다양한 마케팅 솔루션을 이용하고 싶은 고객</h3>
                        <p>AWS, 구글 클라우드를 활용하여 서비스개발을 하고자 한다면, 에네이 마케팅 솔루션을 사용하기 위해서는연동을위한 토큰키값 또는 IP연동에 대한 번거로움이 존재
                            합니다. <br/>
                            에네이클라우드로 통합관리로 번거로움을 해결하세요! </p>
                    </li>
                </ul>
            </div>

            <div className="function_list">
                <h2 className="title_style_3">주요기능</h2>
                <div className="tb_style_1">
                    <table>
                        <colgroup>
                            <col style={{width:"25%"}}/>
                            <col style={{width:"25%"}}/>
                            <col style={{width:"25%"}}/>
                            <col style={{width:"25%"}}/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col" className="blue_bg">리눅스</th>
                            <th scope="col">웹호스팅</th>
                            <th scope="col">SINGLE 클라우드</th>
                            <th scope="col">STANDARD 클라우드</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>트래픽</td>
                            <td>무제한</td>
                            <td>무제한</td>
                            <td>무제한</td>
                        </tr>
                        <tr>
                            <td>DB</td>
                            <td>무제한 동시 허용 접속 수 30conn</td>
                            <td>-</td>
                            <td>-</td>
                        </tr>
                        <tr>
                            <td>VCPU</td>
                            <td>-</td>
                            <td>2</td>
                            <td>2</td>
                        </tr>
                        <tr>
                            <td>RAM</td>
                            <td>-</td>
                            <td>4G</td>
                            <td>16G</td>
                        </tr>
                        <tr>
                            <td>DISK</td>
                            <td>-</td>
                            <td>60G</td>
                            <td>120G</td>
                        </tr>
                        <tr>
                            <td>설치비</td>
                            <td>30,000원</td>
                            <td>30,000원</td>
                            <td>30,000원</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <ul className="list_style_2 clfx">
                    <li>
                        <div className="icon"><img src={cloud_icon1} alt=""/></div>
                        <div className="txt_area">
                            <h3>이미지</h3>
                            <p>
                                서비스 운영에 영향 없이 설정한 자동 백업 일정에 맞게 신속하게 <br/>
                                서버를 백업하고 장애 발생 시 원하는 시점으로 클라우드 서버를 <br/>
                                OS까지 복원할 수 있는 서비스
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=393">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={cloud_icon2} alt=""/></div>
                        <div className="txt_area">
                            <h3>스냅샷</h3>
                            <p>
                                운영 중인 클라우드 서버의 특정 시점을 사진을 찍듯이 찍어서 보관하는 것을 <br/>
                                말합니다. 생성한 스냅샷으로 복구를 하시면 클라우드 서버는 스냅샷의 시점 <br/>
                                으로 복원됩니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=394">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={cloud_icon3} alt=""/></div>
                        <div className="txt_area">
                            <h3>웹방화벽</h3>
                            <p>
                                HTTP/HTTPS 프로토콜에 대한 트래픽을 감시하여 공격을 탐지하고 <br/>
                                해당 공격이 웹 서버에 도달하기 전에 차단하는 보안 솔루션 입니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=399">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={cloud_icon4} alt=""/></div>
                        <div className="txt_area">
                            <h3>공인고정IP</h3>
                            <p>
                                고정 IP는 할당 받은 IP 주소를 그 사람이 독점으로 사용 할 수있는 IP를 <br/>
                                제공합니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=341">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={cloud_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>VPN서비스</h3>
                            <p>
                                전화 부재중 또는 통화종료 후 발신자에게 이벤트, 홍보관련 메시지 전송 <br/>
                                SMS / LMS / MMS 모두 확인 가능
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=399">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div className="icon"><img src={cloud_icon6} alt=""/></div>
                        <div className="txt_area">
                            <h3>SSL VPN</h3>
                            <p>
                                서버 또는 내부 네트워크 접속시 SSL VPN 인증을 통해  허가 가능자만 <br/>
                                접속이 가능하도록 하는 서비스
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=392">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div className="icon"><img src={cloud_icon7} alt=""/></div>
                        <div className="txt_area">
                            <h3>NMS</h3>
                            <p>
                                네트워크 메니지먼트 시스템 중 PRTG NETWORK MONITOR 솔루션 <br/>
                                에네이 클라우드에서  빠르게 설정하여 제공 합니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=338">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div className="icon"><img src={cloud_icon8} alt=""/></div>
                        <div className="txt_area">
                            <h3>ssl인증서</h3>
                            <p>
                                고정 IP는 할당 받은 IP 주소를 그 사람이 독점으로 사용 할 수있는 IP를 <br/>
                                제공합니다.
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=1&id=340">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>


                </ul>
            </div>
        </section>
    );
};

export default Cloud;