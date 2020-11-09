import React from 'react';
import {Link} from 'react-router-dom';
import ServiceSubMenu from "./ServiceSubMenu";

const ServiceSideMenu = ({cx,history,location}) => {
    return (
        <section className={cx("lnb")}>
            <div className={cx("menu")}>
                <ul>
                    <li className={cx(`${location.pathname === "/service/patchcall" ? "on" : ""}`)}><Link to="/service/patchcall"><img src={require('../../../asset/image/service/gnb_icon1.png')} alt="패치콜"/></Link></li>
                    <li className={cx(`${location.pathname === "/service/customer" ? "on" : ""}`)}><Link to="/service/customer"><img src={require('../../../asset/image/service/gnb_icon4.png')} alt="패치AI"/></Link></li>
                    <li className={cx(`${location.pathname === "/service/cloud" ? "on" : ""}`)}><Link to="/service/cloud"><img src={require('../../../asset/image/service/gnb_icon3.png')} alt=""/></Link></li>
                    {/*<li><Link to=""><img src={require('../../../asset/image/service/gnb_icon4.png')} alt=""/></Link></li>*/}
                    <li className={cx(`${location.pathname === "/service/message" ? "on" : ""}`)}><Link to="/service/message"><img src={require('../../../asset/image/service/gnb_icon5.png')} alt=""/></Link></li>
                    {/*<li><Link to=""><img src={require('../../../asset/image/service/gnb_icon6.png')} alt=""/></Link></li>*/}
                    <li className={cx(`${location.pathname === "/service/api" ? "on" : ""}`)}   ><Link to="/service/api"><img src={require('../../../asset/image/service/gnb_icon7.png')} alt=""/></Link></li>
                </ul>
            </div>
            <ServiceSubMenu cx={cx}/>
        </section>
    );
};

export default ServiceSideMenu;