import React from 'react';
import footerLogo from '../../asset/image/footer_logo.jpg';
import {Link} from 'react-router-dom'
import {withRouter} from 'react-router'

const Footer = () => {

    // const exclusionArray = [
    //     '/admin',
    //     '/admin/dashboard',
    //     '/admin/users',
    //     '/admin/coupon',
    //     '/admin/customUserCount',
    //     '/admin/customer_case',
    //     '/admin/customer_case/write',
    //     '/admin/coloring',
    //     '/service',
    //     '/auth/login',
    // ]
    //
    // if(exclusionArray.indexOf(location.pathname) !== -1){
    //     return null;
    // }

    return (
        <div className="footer_wrap">
            <div className="footer clfx">
                <div className="footer_logo"><img src={footerLogo} alt="eney"/></div>
                <div className="footer_cont">
                    <div className="menu">
                        <ul className="clfx">
                            <li><Link to="/introduce/company">회사소개</Link></li>
                            <li><Link to="/customerservice?type=terms">이용약관</Link></li>
                            <li><Link to="/customerservice?type=terms&id=410" style={{color:"red"}}>개인정보처리방침</Link></li>
                            <li><Link to="/customerservice?type=notice">채용공고</Link></li>
                            {/*<li><a href="#">IR</a></li>*/}
                        </ul>
                    </div>
                    <div className="name">(주) 에네이</div>
                    <div className="info">
                        <ul>
                            <li>대표자 : 전재혁</li>
                            <li>사업자등록번호 : 119-87-01395</li>
                            <li>통신판매번호 : 제2015-서울동작-0592호 주식회사 에네이</li>
                        </ul>
                        <ul>
                            <li>별정통신사업자등록번호 : 제 1-01-15-0014호</li>
                            <li>특수부통신사업자등록번호 : 제 3-01-17-0062호</li>
                        </ul>
                        <ul>
                            <li>주소 : 서울특별시 금천구 디지털로9길 41 삼성IT헤링턴타워 507호</li>
                            <li>개인정보관리책임자 : 조명준 (wer01129@eney.co.kr)</li>
                            <li>팩스 : 070-7815-2201</li>
                        </ul>
                    </div>
                    <p className="copyright">Copyright &copy; eney Corp. 2014~2020 All Rights Reserved</p>
                </div>
            </div>
        </div>
    );
};

export default React.memo(Footer);