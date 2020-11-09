import React from 'react';
import {Link, withRouter} from 'react-router-dom';
import {makeStyles} from "@material-ui/core";
import patch from '../../asset/image/service_introduce/patch.png';
import patchAi from '../../asset/image/service_introduce/patch_ai.png';
import patchBi from '../../asset/image/service_introduce/patch_bi.png';
import cloud from '../../asset/image/service_introduce/cloud.png';
import message from '../../asset/image/service_introduce/message.png';
import builder from '../../asset/image/service_introduce/builder.png';
import web from '../../asset/image/service_introduce/web.png';
import colorring from '../../asset/image/service_introduce/colorring.png';
import consulting from '../../asset/image/service_introduce/consulting.png';
import style from './ServiceIntroduce.module.css';
import classnames from "classnames/bind";

const cx = classnames.bind(style);

const ServiceIntroduce = ({match}) => {

    return (
        <section className="sub_container">
        <div className="main_container service_introduce">
            <div className="title_cont">
                <h1 className="title_style_5">Cloud Plaform for your Business</h1>
                <p className="txt_style_1">클라우드 기반의 스마트한 비지니스 솔루션을 제공합니다.</p>
                <div className="btn_area">
                    <a href="#" className="basic-btn01 btn-sky-bg">서비스 이용하기</a>
                </div>
            </div>

            <section className="main_cont cont_2">
                <div>
                <ul className={cx('ul')}>
                    <li className={cx('firstLi')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/patch_call`} >
                            <img src={patch} />
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>패치콜</p>
                        <div className={cx('txtBox')}>
                            가상번호 및 대표번호를 활용한 콜데이터 트래킹솔루션으로 안심번호 및 착신전화기능을 통해 전화문의 경로 유입을 확인 할 수 있습니다.
                        </div>
                    </li>
                    <li className={cx('li')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/patch_ai`} >
                                <img src={patchAi}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>패치AI</p>
                        <div className={cx('txtBox')}>
                            자동 고객프로파일링 솔루션으로 에네이 플랫폼과 연동되어 있는 고객의 이벤트 기록을 자동 수집하여 관리하며, 잠재고객유형 또는 빅데이터 분석을 통한
                            마게팅의 씨드데이터를 수집 할 수 있는 CRM 솔루션 입니다.
                        </div>
                    </li>
                    <li className={cx('li')}>
                        <div className={cx('imageBox')}>
                            {/*to={`${match.url}/patch_bi`}*/}
                            <Link to="/service_introduce" onClick={() => {alert("서비스 준비중입니다")}} >
                                <img src={patchBi}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>패치BI</p>
                        <div className={cx('txtBox')}>
                            운영하고있는 비즈니스플랫폼 강화를 위해서 만들어진 비즈니스 솔루션입니다.
                            자신이 수집한 데이터를 기준으로 다양한 커머셜데이터와 비교하여 마케팅인사이트를 확보 할 수 있습니다
                        </div>
                    </li>
                    <li className={cx('firstLi')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/cloud`} >
                                <img src={cloud}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>클라우드</p>
                        <div className={cx('txtBox')}>
                            오픈스택기반 클라우드 컴퓨팅 기반의 컴퓨팅 및 네트워크 서비스를 제공합니다.
                        </div>
                    </li>
                    <li className={cx('li')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/message`} >
                                <img src={message}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>패치 메세징</p>
                        <div className={cx('txtBox')}>
                            다이렉트 마케팅을 위한 대량문자 전송서비스 및 알림톡 친구톡을 이용 할 수 있습니다. 패치AI를 통해 고객별 이력관리가 연동됩니다.
                        </div>
                    </li>
                    <li className={cx('li')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/builder`} >
                                <img src={builder}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>웹/앱 빌더</p>
                        <div className={cx('txtBox')}>
                            빌더를 활용하여 온라인비지니스 플랫폼을 합리적인 비용으로 구축 및 운영이 가능합니다.
                        </div>
                    </li>
                    <li className={cx('firstLi')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/patch_call`} >
                                <img src={web}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>웹디자인 제작</p>
                        <div className={cx('txtBox')}>
                            웹디자인, 로고, 팜플렛, 포스터 제작등 디자인 관련 3rd Part협력사와 에네이 용역관리프로세스를 통해 만들어갑니다.
                        </div>
                    </li>
                    <li className={cx('li')}>
                        <div className={cx('imageBox')}>
                            <Link to={`${match.url}/patch_call?target=colorring_area`} >
                                <img src={colorring}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>컬러링 제작</p>
                        <div className={cx('txtBox')}>
                            핸드폰 및 대표번호 컬러링을 제작해 드립니다. 회사전화부터 직원들의 핸드폰에 기업의 컬러링을 적용이 가능합니다.
                        </div>
                    </li>
                    <li className={cx('li')}>
                        <div className={cx('imageBox')}>
                            {/*<Link to={`${match.url}/patch_call`} >*/}
                            <Link to={`/customer_case`} >
                                <img src={consulting}/>
                            </Link>
                        </div>
                        <p className={cx('subTitle')}>교육/컨설팅 아카데미</p>
                        <div className={cx('txtBox')}>
                            에네이클라우드 기반 웹프로젝트
                            구축을 위한 온라인 교육 및 컨설팅을 제공 합니다.
                        </div>
                    </li>

                </ul>
                </div>
            </section>
        </div>
        </section>
    );
};

export default withRouter(ServiceIntroduce);