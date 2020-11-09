import React,{useState,useEffect} from 'react';
import {withRouter} from 'react-router-dom';
import Helmet from 'react-helmet';

const HelmetController = ({location}) => {
    const [helmet, setHelmet] = useState(null);

    useEffect(() => {
        createHelmet();

    },[location])

    const createHelmet = () => {

        let html = null;
        switch (location.pathname) {
            case "/":
                html =
                    <Helmet>
                        <title>ENEY 개발자 마켓터 데이터분석가 필요한 사람들을 위한 클라우드플랫폼</title>
                        <meta name="description" content="개발자,마켓터, 데이터분석가 필요한 사람들을 위한 클라우드플랫폼" />
                        <meta name="keywords" content="안심번호, 가상번호, 050, 0505,0506, 타지역, 착신전환, 안심번호050,자동고객관리, 객관리, 상담솔루션, CRM구축, 업무자동화, CRM업체, 로그분석, 회원관리프로그램, 빅데이터,대량문자, 문자사이트, 문자발송, 문자보내기, 단체 문자, 인터넷문자, 웹문자, 예약문자" />
                    </Helmet>
                break;
            case "/service_introduce/patch_call":
                if(location.hash === "#colorring_area"){
                    html =
                        <Helmet>
                            <title>전문성우 컬러링 제작소</title>
                            <meta name="description" content="전문성우 컬러링 제작소" />
                            <meta name="keywords" content="안심번호, 가상번호, 050, 0505,0506, 타지역, 착신전환, 안심번호050, 컬러링" />
                        </Helmet>

                }else{
                    html =
                        <Helmet>
                            <title>국내최초 가상번호  RestAPI 제공 사업자</title>
                            <meta name="description" content="국내최초 가상번호  RestAPI 제공 사업자" />
                            <meta name="keywords" content="안심번호, 가상번호, 050, 0505,0506, 타지역, 착신전환, 안심번호050" />
                        </Helmet>
                }
                break;
            case "/service_introduce/patch_ai":
                html =
                    <Helmet>
                        <title>자동 컨택센터 솔루션</title>
                        <meta name="description" content="자동 컨택센터 솔루션" />
                        <meta name="keywords" content="자동고객관리, 고객관리, 상담솔루션, CRM구축, 업무자동화, CRM업체, 로그분석, 회원관리프로그램, 빅데이터" />
                    </Helmet>
                break;
            case "/service_introduce/message":
                html =
                    <Helmet>
                        <title>다이렉트마켓팅의 시작- 문자, 알림톡, 이메일</title>
                        <meta name="description" content="다이렉트마켓팅의 시작- 문자, 알림톡, 이메일" />
                        <meta name="keywords" content="대량문자, 문자사이트, 문자발송, 문자보내기, 단체 문자, 인터넷문자, 웹문자, 예약문자" />
                    </Helmet>
                break;
            case "/service_introduce/builder":
                html =
                    <Helmet>
                        <title>Nested Title</title>
                        <meta name="description" content="웹/앱 분양사무소" />
                        <meta name="keywords" content="홈페이지제작, 홈페이지제작업체,웹디자인,홈페이지리뉴얼,사이트등록,사이트제작, 홈페이지제작비용,반응형웹,병원홈페이지제작, 기업홈페이지, 웹에이전시,반응형홈페이지 제작" />
                    </Helmet>
                break;
            case "/service_introduce/cloud":
                html =
                    <Helmet>
                        <title>웹/앱 서버 전용 클라우드 플랫폼</title>
                        <meta name="description" content="웹/앱 서버 전용 클라우드 플랫폼" />
                        <meta name="keywords" content="웹호스팅, 오픈스택클라우드, 프라이빗 클라우드" />
                    </Helmet>
                break;
        }
        setHelmet(html)
    }

    return helmet;

    // return (
    //     {/*<div>*/}
    //         {helmet}
    //     {/*</div>*/}
    // );
};

export default withRouter(HelmetController);