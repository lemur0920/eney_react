import React,{useState,useEffect} from 'react';
import { Link,withRouter } from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import {initUserType} from "../../modules/auth";
const UserTypePage = ({history}) => {

    const dispatch = useDispatch();

    const {user} = useSelector(({auth}) => ({
        user:auth.user,
    }))


    const onClick = (type) =>{
        dispatch(initUserType(type))
        history.push("/auth/agreement_check");
    }

    useEffect(() => {
        if(user){
            alert("이미 로그인되어있습니다")
            history.push('/')
        }
    }, [user])

    return (
        <section className="sub_container">
            <div className="join_type_area">
                <h1 className="title_style_4">ENEY 회원가입</h1>
                <div className="join_type">
                <ul className="clfx">
                    <li className="individual">
                        {/*<Link to={{pathname:"/auth/agreement_check", userType: "personal"}} ><span className="title">개인</span></Link>*/}
                        <Link onClick={() => onClick('personal')} to="#"><span className="title">개인</span></Link>
                    </li>
                    <li className="company">
                        {/*<Link to={{pathname:"/auth/agreement_check", userType: "corporate"}} ><span className="title">사업자</span></Link>*/}
                        <Link onClick={() => onClick('corporate')} to="#"><span className="title">사업자</span></Link>
                    </li>
                </ul>
                    <p className="txt_1">
                        개인회원이시면 개인을 사업자 이시면 사업자를 클릭해주세요. <br/>
                        사업자 회원께서는 <span className="f-blue">사업자등록증 사본</span>을 미리 준비해 주세요.
                    </p>
                </div>
            </div>
        </section>
    );
};

export default withRouter(UserTypePage);