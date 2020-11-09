import React,{Fragment} from 'react';
import CompanyIntroduceTab from "./CompanyIntroduceTab";

const CompanyMap = ({page}) => {
    return (
        <Fragment>

            <section className="main_cont">
                <h1 className="title_style_5">오시는길</h1>
                <p className="txt_style_1">에네이는 언제나 여러분을 환영합니다.</p>
            </section>

            <CompanyIntroduceTab page={page}/>

            <div className="location_wrap">
                <div className="map_area">
                    <iframe
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1582.755786331246!2d126.9588233582313!3d37.49585082301154!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca1d136f21133%3A0xbdeac402fc79b93d!2z7ISc7Jq47Yq567OE7IucIOyDgeuPhOuPmSDsiK3si6TrjIDtlZnqtZAg7IKs7ZqM6rO87ZWZ6rSA!5e0!3m2!1sko!2skr!4v1514828867349"
                        frameBorder="0" allowFullScreen/>

                </div>

                <div className="location_list">
                    <ul className="clfx">
                        <li>
                            <h2>주소</h2>
                            <p>서울특별시 동작구 상도로 369 숭실대학교 창신관 212호</p>
                        </li>
                        <li>
                            <h2>Tel / Fax</h2>
                            <p>Tel 050-6191-0024 / Fax 070-7815-2201</p>
                        </li>
                    </ul>
                </div>

                <div className="traffic_info">
                    <h2>교통안내</h2>
                    <ul className="clfx">
                        <li className="sub_way">
                            <h3>지하철</h3>
                            <div><span className="line_7">7</span>지하철 7호선 숭실대입구역 3번출구에서 도보 10분</div>
                        </li>
                        <li className="bus">
                            <h3>버스노선도</h3>
                            <div><span className="bus_blue">간선</span>751, 752, 753</div>
                            <div><span className="bus_green">마을</span>동작14</div>
                        </li>
                    </ul>
                </div>
            </div>
        </Fragment>
    );
};

export default CompanyMap;