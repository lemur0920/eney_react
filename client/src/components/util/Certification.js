import React,{useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getCertify, idFind,pwRecover} from "../../modules/auth";
import { withRouter } from 'react-router-dom';
import { withUserAgent } from 'react-useragent';

const Certification = ({onClose, type}) => {

    const {isCertify,recoverId} = useSelector(({auth}) =>({
        isCertify:auth.isCertify,
        recoverId:auth.pwFind.userId,
    }))
    const dispatch = useDispatch();

    useEffect( () => {
        const isBrowser = process.env.APP_ENV === 'browser';

        if(isBrowser) {
            const { IMP } = window;
            console.log(window)
            console.log(IMP)
            // if(IMP !== undefined){
            //     IMP.init('imp73315648');
            //
            //     /* 2. 본인인증 데이터 정의하기 */
            //     const data = {
            //         // company: 'eney',                           // 회사명 또는 URL
            //         // carrier: 'KTF',                              // 통신사
            //         // name: '황규철',                                // 이름
            //         // phone: '01090509223',                        // 전화번호
            //     };
            //     IMP.certification(data, callback);
            //
            // }

            // let w = window;
            // let s = document.createElement('script');
            // s.type = 'text/javascript';
            // s.async = true;
            // s.src = 'https://cdn.iamport.kr/js/iamport.payment-1.1.7.js';
            // s.charset = 'UTF-8';
            //
            // let j = document.createElement('script');
            // j.type = 'text/javascript';
            // j.async = true;
            // j.src = 'https://code.jquery.com/jquery-1.12.4.min.js';
            // j.charset = 'UTF-8';
            //
            //
            // let x = document.getElementsByTagName('script')[0];
            // let y = document.getElementsByTagName('script')[1];
            // x.parentNode.insertBefore(s, x);
            // y.parentNode.insertBefore(j, y);
            if(IMP !== undefined){
                    IMP.init('imp73315648');

                    /* 2. 본인인증 데이터 정의하기 */
                    const data = {
                        // company: 'eney',                           // 회사명 또는 URL
                        // carrier: 'KTF',                              // 통신사
                        // name: '황규철',                                // 이름
                        // phone: '01090509223',                        // 전화번호
                    };
                    IMP.certification(data, callback);

                }
            // IMP.init('imp73315648');
            //
            // /* 2. 본인인증 데이터 정의하기 */
            // const data = {
            //     // company: 'eney',                           // 회사명 또는 URL
            //     // carrier: 'KTF',                              // 통신사
            //     // name: '황규철',                                // 이름
            //     // phone: '01090509223',                        // 전화번호
            // };
            // IMP.certification(data, callback);
        }

    },[])

    // useEffect( () =>{
        // const scriptId = 'iamport_script';
        // const scriptUrl = 'https://cdn.iamport.kr/js/iamport.payment-1.1.7.js';
        // const isExist = !!document.getElementById(scriptId);
        //
        // if (!isExist) {
        //     const script = document.createElement('script');
        //     script.src = scriptUrl;
        //     script.onload = () => this.initiate(this);
        //     script.onerror = error => this.handleError(error);
        //     script.id = scriptId;
        //     document.body.appendChild(script);
        // } else this.initiate(this);
        // const { IMP } = window;
        // IMP.init('imp73315648');
        //
        // /* 2. 본인인증 데이터 정의하기 */
        // const data = {
        //     // company: 'eney',                           // 회사명 또는 URL
        //     // carrier: 'KTF',                              // 통신사
        //     // name: '황규철',                                // 이름
        //     // phone: '01090509223',                        // 전화번호
        // };
        // IMP.certification(data, callback);
        //


    // })
    // function onClickCertification() {
        /* 1. 가맹점 식별하기 */
        // const { IMP } = window;
        // IMP.init('imp73315648');
        //
        // /* 2. 본인인증 데이터 정의하기 */
        // const data = {
        //     // merchant_uid: `mid_${new Date().getTime()}`  // 주문번호
        //     company: '아임포트',                           // 회사명 또는 URL
        //     carrier: 'SKT',                              // 통신사
        //     name: '홍길동',                                // 이름
        //     phone: '01012341234',                        // 전화번호
        // };

        /* 4. 본인인증 창 호출하기 */
        // IMP.certification(data, callback);
    // }


    /* 3. 콜백 함수 정의하기 */
    function callback(response) {
        onClose()
        console.log(response)
        const {
            success,
            merchant_uid,
            error_msg,
        } = response;

        if (success) {
            if(type === "ID_FIND"){
                dispatch(idFind(response.imp_uid));
                return
            }

            if(type === "PW_RECOVER"){
                dispatch(pwRecover(response.imp_uid, recoverId));
                return
            }

            if(type === "PHONE_CHANGE" || type === "JOIN"){
                dispatch(getCertify(response.imp_uid, type));
            }

            alert('본인인증 성공');
        } else {
            alert(`본인인증 실패: ${error_msg}`);
        }
    }

    // IMP.certification(data, callback);

    return (
        null
    );
};


export default withUserAgent(withRouter(Certification));