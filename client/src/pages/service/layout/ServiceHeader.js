import React from 'react';
import {Link} from 'react-router-dom';
import {logout} from "../../../modules/auth";
import {useDispatch} from "react-redux";

const ServiceHeader = ({cx}) => {
    const dispatch = useDispatch();

    const onLogout = () => {
        try{
            localStorage.removeItem("user");
            dispatch(logout())
            history.push("/")
        }catch(e){
            console.log("localStorage is not working")
        }
    }

    return (
        <div className={cx("header")}>
            <div className={cx("logo")}><Link to="/"><img src={require('../../../asset/image/service/logo.jpg')}  alt="eney"/></Link></div>
            <div className={cx("right_area")}>
                {/*<div className={cx("search")}>*/}
                {/*    <input type="text"/>*/}
                {/*    <button className={cx("btn_search")}><img src={require('../../../asset/image/service/top_btn_search.gif')} alt="검색"/></button>*/}
                {/*</div>*/}
                <ul className={cx("member_menu")}>
                    <li><Link to="/mypage">마이에네이</Link></li>
                    <li><Link to="/" onClick={() =>{onLogout();}}>로그아웃</Link></li>
                </ul>
            </div>
        </div>
    );
};

export default ServiceHeader;