import React,{useEffect} from 'react';
import patch_ai_img from '../../asset/image/patch_ai_img.jpg'
import patch_bi_icon1 from '../../asset/image/patch_bi_icon1.jpg'
import patch_bi_icon2 from '../../asset/image/patch_bi_icon2.jpg'
import patch_bi_icon3 from '../../asset/image/patch_bi_icon3.jpg'
import patch_bi_icon4 from '../../asset/image/patch_bi_icon4.jpg'
import patch_bi_icon5 from '../../asset/image/patch_bi_icon5.jpg'
import patch_bi_icon6 from '../../asset/image/patch_bi_icon6.jpg'
import patch_bi_icon7 from '../../asset/image/patch_bi_icon7.jpg'
import swal from 'sweetalert';

const PatchBi = () => {

    swal("서비스 준비중입니다");
    return (
        <section className="sub_container">
            <div className="navi clfx">
                <ul className="clfx">
                    <li>Home</li>
                    <li>서비스</li>
                    <li>패치AI</li>
                </ul>
            </div>

            <div className="sub_title_area clfx">
                <div className="img_area"><img src={patch_ai_img} alt=""/></div>
                <div className="txt_area">
                    <h1 className="title_style_2">패치BI</h1>
                    <p className="txt_style_2">
                        패치BI는 운영하고 있는 비즈니스 플랫폼 강화를 위해서 고안된 비즈니스 인텔리션스 솔루션 입니다. <br/>
                        자신이 수집한데이터를 기준으로 경쟁사의 다양한 커머셜데이터와 비교하여 마케팅인사이트를 확보 할 수 있도록 기획 되어 졌습니다.

                    </p>
                    <div className="btn_area">
                        <a href="#" className="basic-btn01 btn-sky-bg">서비스 이용하기</a>
                    </div>
                </div>
            </div>

            <div className="use_method">
                <h2 className="title_style_3">활용법</h2>
                <ul className="list_style_1">
                    <li>
                        <h3>1. O2O플랫폼을 운영하면서 새로운 비즈니스 기회가 필요한 사업자</h3>
                    </li>
                    <li>
                        <h3>2. 자사몰을 운영하면서 온라인 디지털광고 관리가 필요한 사업자</h3>
                    </li>
                </ul>
            </div>

            <div className="function_list bd_none">
                <h2 className="title_style_3">주요기능</h2>

                <div className="sub_title_style_1">O2O플랫폼 인사이트</div>

                <ul className="list_style_2 clfx">
                    <li>
                        <div className="icon"><img src={patch_bi_icon1} alt=""/></div>
                        <div className="txt_area">
                            <h3>자신의 데이터와 업종 및 유형데이터와의 비교</h3>
                            <p>
                                패치콜 플랫폼으로 유입되는 콜로그 기준으로 자동고객 리스트를 <br/>
                                생성하여 타겟광고 및 다이렉트 마케팅을 위한 데이터로 활용됩니다.
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_bi_icon2} alt=""/></div>
                        <div className="txt_area">
                            <h3>제휴사 마케팅 현황 파악</h3>
                            <p>
                                고객데이터가 없어도, 업종 및 유형에 따라 타겟광고 할 수 있으며,
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_bi_icon3} alt=""/></div>
                        <div className="txt_area">
                            <h3>제휴사 상권 분석</h3>
                            <p>
                                대량문자 및 알림톡 서비스 입니다. 경쟁사 대비 차별화 포인트는 다이렉트 <br/>
                                마케팅의 발송시 개인화url를 활용한 효과분석서비스를 제공합니다.
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                </ul>

                <div className="sub_title_style_1">쇼핑몰 인사이트</div>

                <ul className="list_style_2 clfx">
                    <li>
                        <div className="icon"><img src={patch_bi_icon4} alt=""/></div>
                        <div className="txt_area">
                            <h3>카테고리 전체 시장동향과 경쟁사 데이터 비교 </h3>
                            <p>
                                고객프로파일링 기반 자동타겟광고 서비스 제공 One Click 으로 <br/>
                                타겟광고를 할 수 있습니다.
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_bi_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>자사 플랫폼이 포함된 카테고리에 시장동향 파악</h3>
                            <p>
                                문자발송시 개인화url를 통해 목적지 페이지 <br/>
                                데이터 수집 분석이 가능 합니다.
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_bi_icon6} alt=""/></div>
                        <div className="txt_area">
                            <h3>경쟁사의 실시간 디지털 마케팅 및 온라인상 브랜드 인지도 파악 </h3>
                            <p>
                                고객프로파일링 기반 자동타겟광고 서비스 제공 One Click 으로 <br/>
                                타겟광고를 할 수 있습니다.
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_bi_icon7} alt=""/></div>
                        <div className="txt_area">
                            <h3>매주/월 매출 성장 및 디털마케팅 획득 분석 </h3>
                            <p>
                                고객프로파일링 기반 자동타겟광고 서비스 제공 One Click 으로 <br/>
                                타겟광고를 할 수 있습니다.
                            </p>
                            <div className="btn_area">
                                <a href="#">→ 자세히 알아보기</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

        </section>
    );
};

export default PatchBi;