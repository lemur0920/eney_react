import React,{useEffect} from 'react';
import {withRouter} from 'react-router-dom';

const JoinSuccessPage = ({history}) => {

    useEffect( () => {
        setTimeout(
            function() {
                history.push("/")
            }
                .bind(this),
            3000
        );
    })

    return (
        <section className="sub_container">
            회원가입 완료
            로그인 후 에네이 서비스를 이용하세요
        </section>
    );
};

export default withRouter(JoinSuccessPage);