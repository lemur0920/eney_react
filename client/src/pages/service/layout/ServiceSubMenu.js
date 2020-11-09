import React from 'react';
import {Link} from "react-router-dom";
import UsersPage from "../../admin/users/UsersPage";
import CouponPage from "../../admin/coupon/CouponPage";
import CustomUserCountPage from "../../admin/custom_user_count/CustomUserCountPage";
import CustomerCasePage from "../../admin/customer_case/CustomerCasePage";
import ColoringPage from "../../admin/coloring/ColoringPage";
import {withRouter, Switch,Route} from 'react-router-dom';

const ServiceSubMenu = ({cx}) => {
    return (
        <div className={cx("s_lnb_list")}>
            <ul>
                <Switch>
                    {/*<Route path="/admin/users" component={UsersPage} />*/}
                    {/*<Route path="/admin/coupon" component={CouponPage} />*/}
                    {/*<Route path="/admin/customUserCount" component={CustomUserCountPage} />*/}
                    {/*<Route path="/admin/customer_case" component={CustomerCasePage} />*/}
                    {/*<Route path="/admin/coloring" component={ColoringPage} />*/}
                    <Route path="/service/patchcall" render={ () => (
                            <li className={cx("active")}>
                                <div className={cx("s_lnb")}>
                                    <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>
                                    <span className={cx("title")}>패치콜 관리</span>
                                    <div className={cx("s_menu")}>
                                        <ul>
                                            <li><Link to="/service/patchcall/dashboard">대시보드</Link></li>
                                            <li><Link to="/service/patchcall/management">번호관리</Link></li>
                                            <li><Link to="/service/patchcall/call_log">수신내역</Link></li>
                                            <li><Link to="/service/patchcall/cid">발신번호리스트</Link></li>
                                            <li><Link to="/service/patchcall/coloring">컬러링</Link></li>
                                            <li><Link to="/service/patchcall/analytics_manage">애널리틱스 관리</Link></li>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                        )}
                    />
                    <Route path="/service/customer" render={ () => (
                        <li className={cx("active")}>
                            <div className={cx("s_lnb")}>
                                <div className={cx("icon")}><img src={require('../../../asset/image/service/patchai.png')} alt=""/></div>
                                <span className={cx("title")}>패치 AI</span>
                                <div className={cx("s_menu")}>
                                    <ul>
                                        <li><Link to="/service/customer/management">고객관리</Link></li>
                                        <li><Link to="/service/customer/group/management">그룹관리</Link></li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    )}
                    />
                    <Route path="/service/cloud" render={ () => (
                        <li className={cx("active")}>
                            <div className={cx("s_lnb")}>
                                <div className={cx("icon")}><img src={require('../../../asset/image/service/cloud.png')} alt=""/></div>
                                <span className={cx("title")}>패치 CLOUD</span>
                                <div className={cx("s_menu")}>
                                    <ul>
                                        <li><Link to="/service/cloud">클라우드 관리</Link></li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    )}
                    />

                    <Route path="/service/message" render={ () => (
                        <li className={cx("active")}>
                            <div className={cx("s_lnb")}>
                                <div className={cx("icon")}><img src={require('../../../asset/image/service/message.png')} alt=""/></div>
                                <span className={cx("title")}>메세지</span>
                                <div className={cx("s_menu")}>
                                    <ul>
                                        <li><Link to="/service/message/send">문자 전송</Link></li>
                                        <li><Link to="/service/message/template">문자 템플릿</Link></li>
                                        <li><Link to="/service/message/result">결과조회</Link></li>
                                        <li><Link to="/service/message/kakao/profile/list">카카오 발신프로필</Link></li>
                                        <li><Link to="/service/message/kakao/alimtalk">카카오 알림톡 템플릿</Link></li>
                                    </ul>
                                </div>
                            </div>
                        </li>
                    )}
                    />

                    <Route path="/service/api" render={ () => (
                        <li className={cx("active")}>
                            <div className={cx("s_lnb")}>
                                <div className={cx("icon")}><img src={require('../../../asset/image/service/api.png')} alt=""/></div>
                                <span className={cx("title")}>개발자센터</span>
                                <div className={cx("s_menu")}>
                                    <ul>
                                        <li><Link to="/service/api/">API DOCUMENT</Link></li>
                                        {/*<li><Link to="/service/patchcall/call_log">수신내역</Link></li>*/}
                                        {/*<li><Link to="/service/patchcall/cid">발신번호리스트</Link></li>*/}
                                        {/*<li><Link to="/service/patchcall/coloring">컬러링</Link></li>*/}
                                    </ul>
                                </div>
                            </div>
                        </li>
                    )}
                    />

                    {/*<li className={cx("active")}>*/}
                {/*    <div className={cx("s_lnb")}>*/}
                {/*        <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>*/}
                {/*        <span className={cx("title")}>패치콜 관리2</span>*/}
                {/*        <div className={cx("s_menu")}>*/}
                {/*            <ul>*/}
                {/*                <li className={cx("on")}><a href="#">번호관리</a></li>*/}
                {/*                <li><a href="#">수신내역</a></li>*/}
                {/*                <li><a href="#">발신번호리스트</a></li>*/}
                {/*                <li><a href="#">컬러링</a></li>*/}
                {/*            </ul>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</li>*/}
                {/*<li>*/}
                {/*    <div className={cx("s_lnb")}>*/}
                {/*        <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>*/}
                {/*        <span className={cx("title")}>패치콜 관리3</span>*/}
                {/*        <div className={cx("s_menu")}>*/}
                {/*            <ul>*/}
                {/*                <li><a href="#">번호관리</a></li>*/}
                {/*                <li><a href="#">수신내역</a></li>*/}
                {/*                <li><a href="#">발신번호리스트</a></li>*/}
                {/*                <li><a href="#">컬러링</a></li>*/}
                {/*            </ul>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</li>*/}
                {/*<li>*/}
                {/*    <div className={cx("s_lnb")}>*/}
                {/*        <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>*/}
                {/*        <span className={cx("title")}>패치콜 관리4</span>*/}
                {/*        <div className={cx("s_menu")}>*/}
                {/*            <ul>*/}
                {/*                <li><a href="#">번호관리</a></li>*/}
                {/*                <li><a href="#">수신내역</a></li>*/}
                {/*                <li><a href="#">발신번호리스트</a></li>*/}
                {/*                <li><a href="#">컬러링</a></li>*/}
                {/*            </ul>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</li>*/}
                {/*<li>*/}
                {/*    <div className={cx("s_lnb")}>*/}
                {/*        <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>*/}
                {/*        <span className={cx("title")}>패치콜 관리5</span>*/}
                {/*        <div className={cx("s_menu")}>*/}
                {/*            <ul>*/}
                {/*                <li><a href="#">번호관리</a></li>*/}
                {/*                <li><a href="#">수신내역</a></li>*/}
                {/*                <li><a href="#">발신번호리스트</a></li>*/}
                {/*                <li><a href="#">컬러링</a></li>*/}
                {/*            </ul>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</li>*/}
                {/*<li>*/}
                {/*    <div className={cx("s_lnb")}>*/}
                {/*        <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>*/}
                {/*        <span className={cx("title")}>패치콜 관리6</span>*/}
                {/*        <div className={cx("s_menu")}>*/}
                {/*            <ul>*/}
                {/*                <li><a href="#">번호관리</a></li>*/}
                {/*                <li><a href="#">수신내역</a></li>*/}
                {/*                <li><a href="#">발신번호리스트</a></li>*/}
                {/*                <li><a href="#">컬러링</a></li>*/}
                {/*            </ul>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</li>*/}
                {/*<li>*/}
                {/*    <div className={cx("s_lnb")}>*/}
                {/*        <div className={cx("icon")}><img src={require('../../../asset/image/service/gnb_top_logo.png')} alt=""/></div>*/}
                {/*        <span className={cx("title")}>패치콜 관리7</span>*/}
                {/*        <div className={cx("s_menu")}>*/}
                {/*            <ul>*/}
                {/*                <li><a href="#">번호관리</a></li>*/}
                {/*                <li><a href="#">수신내역</a></li>*/}
                {/*                <li><a href="#">발신번호리스트</a></li>*/}
                {/*                <li><a href="#">컬러링</a></li>*/}
                {/*            </ul>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*</li>*/}
                </Switch>
            </ul>
        </div>
    );
};

export default ServiceSubMenu;