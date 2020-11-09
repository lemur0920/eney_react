import React, {useState, useEffect, Fragment} from 'react';
import {useSelector,useDispatch} from "react-redux";
import logo from '../../asset/image/logo.png';
import logoSub from '../../asset/image/logo_sub.png';
import { Link } from 'react-router-dom';
import {logout} from '../../modules/auth';

const styles = {
    menu:{transition: '.25s all'},
    show: {
        right: 0
    },
    hidden:{
        display:"none"
    }

}

const Header = ({location,history}) => {

    const [subHeader, setSubHeader] = useState("");
    const [subHeaderLogo, setSubHeaderLogo] = useState(logo);


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

    const {user} = useSelector(({auth}) => ({user:auth.user}))

    const [showMenu, setShowMenu] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
    },[])

    useEffect(() => {
        if(location.pathname === "/" || location.pathname === ''){
            setSubHeader("")
            setSubHeaderLogo(logo)
        }else{
            setSubHeader("sub_header");
            setSubHeaderLogo(logoSub)
        }

    },[location.pathname])


    const onLogout = () => {
        try{
            localStorage.removeItem("user");
            dispatch(logout())
            history.push("/")
        }catch(e){
            console.log("localStorage is not working")
        }
    }
    // useEffect(()=>{
    //     console.log(user)
    //     // if(authError){
    //     //     console.log("오류 발생")
    //     //     setError("아이디 / 패스워드가 일치하지않습니다")
    //     //     return;
    //     // }
    //     // if(auth){
    //     //     console.log("로그인")
    //     //     dispatch(tempSetUser(auth));
    //     // }
    // },[user]);


    // if(location.pathname === '/auth/login' || location.pathname === '/admin'){
    //     return null;
    // }
    // store.dispatch(tempSetUser(user));
    // store.dispatch(check());
    // if(!user){
    //     return <Preloader resolve={()=> dispatch(tempSetUser(user))}/>
    // }

    return (
    <div className={`header_wrap ${subHeader}`}>
            <div className="header">
                <div className="logo"><Link to="/"><img src={subHeaderLogo} alt="eney" /></Link></div>
                <div className="gnb">
                    <ul className="clfx">
                        <li><Link to="/">소개</Link></li>
                        <li><Link to="/service_introduce">서비스</Link></li>
                        <li><Link to="/price">신청/요금 </Link></li>
                        {/*<li><Link to="/customer_case">고객사례</Link></li>*/}
                        <li><Link to="/customerservice">고객센터</Link></li>
                    </ul>
                </div>
                <div className="right_btn clfx">
                    <ul className="clfx">
                        {(user && user.role === "ADMIN") ? (
                            <Fragment>
                                <li><Link to="/admin">관리자</Link></li>
                            </Fragment>
                        ):(
                            null
                        )}
                        {user ? (
                            <Fragment>
                                <li><Link to="/service">서비스</Link></li>
                                <li><Link to="/mypage">마이페이지</Link></li>
                                <li><Link to="#" onClick={onLogout}>로그아웃</Link></li>
                            </Fragment>
                            ):(
                                <Fragment>
                                    <li><Link to="/auth/login">로그인</Link></li>
                                    <li><Link to="/auth/user_type">회원가입</Link></li>
                                </Fragment>
                            )}
                        {/*<li>*/}
                        {/*    <div className="btn_top_search">*/}
                        {/*        <button><img src={btnTopSearch} alt="검색열기" /></button>*/}
                        {/*        <div className="search_area">*/}
                        {/*            <input type="text" />*/}
                        {/*            <button><img src={btnTopSearch} alt="검색시작" /></button>*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*</li>*/}
                    </ul>
                    <div className="menu_open" onClick={() => setShowMenu(!showMenu)} style={Object.assign({}, styles.menu, showMenu && styles.hidden)}>
                        <div className="hamburger hamburger--slider js-hamburger">
                            <div className="hamburger-box">
                                <div className="hamburger-inner"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="total_menu"
                     style={Object.assign({}, styles.menu, showMenu && styles.show)} onClick={() => setShowMenu(!showMenu)}>
                    <div className="hamburger hamburger--slider js-hamburger" style={{width: "100%"}}>
                        <div className="hamburger-box" style={{float: "left",right: "-20px"}}>
                            <div className="hamburger-inner"></div>
                        </div>
                        {user && (
                            <Link to="#" onClick={onLogout} style={{float: "right",marginRight: "20px"}}>로그아웃</Link>
                        )}
                    </div>
                    <div className="login_area">
                        <ul className="clfx">
                            {user ? (
                                <Fragment>
                                    <li><Link to="/service">관리자 콘솔</Link></li>
                                    <li><Link to="/mypage">마이페이지</Link></li>
                                    {/*<li><Link to="#" onClick={onLogout}>로그아웃</Link></li>*/}
                                </Fragment>
                            ):(
                                <Fragment>
                                    <li><Link to="/auth/login">로그인</Link></li>
                                    <li><Link to="/auth/user_type">회원가입</Link></li>
                                </Fragment>
                            )}
                        </ul>
                    </div>
                    <ul className="menu">
                        <li><Link to="/">소개</Link></li>
                        <li><Link to="/service_introduce">서비스</Link></li>
                        <li><Link to="/price">신청/요금</Link></li>
                        {/*<li><Link to="/customer_case">고객사례</Link></li>*/}
                        <li><Link to="/customerservice">고객센터</Link></li>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default React.memo(Header);