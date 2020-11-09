import React, {Fragment} from 'react';
import ColoringApply from "../../container/colorring_apply/ColoringApply";
import style from '../../asset/style/coloring_apply/coloring_apply.module.css';
import classnames from "classnames/bind";

const cx = classnames.bind(style);

const ColoringApplyPage = () => {
    return (
        <Fragment>
            <section className="sub_container">
                <div className="main_container">
                    <section className="main_cont">
                        <h1 className="title_style_5">컬러링 제작</h1>
                        <p className="txt_style_1">보유하고 있는 음원(WAV 16비트 모노 8000hz)이 있다면 파일 업로드를 하여 사용할 수 있으며,<br/>
                            전문 성우가 녹음하는 음원파일을 원하실 경우에는 아래와 같이 온라인 의뢰를 통하여 제작이 가능합니다.</p>
                        {/*<Link to="#" className="service_btn">서비스 이용하기</Link>*/}
                        {/*<p className="txt_style_1">*/}
                        <ul className={cx("helper_text")}>
                            <li>
                                <p>&#8251;제작기간 : 7일 이상, 제작비용 : 30,000point</p>
                            </li>
                            {/*<li>*/}
                            {/*    <p>&#8251;제작비용 : 30,000point</p>*/}
                            {/*</li>*/}
                        </ul>
                        {/*</p>*/}
                        <ColoringApply cx={cx}/>
                    </section>
                    {/*<div className="coloring_regist_info">*/}
                    {/*    <h3>확인해주세요! 컬러링 음원 제작이란?</h3>*/}
                    {/*    <div className="coloring_regist_box">*/}
                    {/*        <p>보유하고 있는 음원(WAV 16비트 모노 8000hz)이 있다면 파일 업로드를 하여 사용할 수 있으며,*/}
                    {/*            전문 성우가 녹음하는 음원파일을 원하실 경우에는 아래와 같이 온라인 의뢰를 통하여 제작이 가능합니다.</p>*/}
                    {/*        <ul>*/}
                    {/*            <li>*/}
                    {/*                <p>제작기간 : 7일 이상</p>*/}
                    {/*            </li>*/}
                    {/*            <li>*/}
                    {/*                <p>제작비용 : 30,000point</p>*/}
                    {/*            </li>*/}
                    {/*        </ul>*/}
                    {/*        <p>제작완료 시 업로드 파일리스트에 자동 업로드</p>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    {/*<Route exact path={`${match.url}`} render={ () => <CustomerCase cx={cx}/>}/>*/}
                    {/*<Route path={`${match.url}/view`} render={ () =>  <CustomerCaseView cx={cx}/>}/>*/}
                </div>
            </section>
        </Fragment>
    );
};

export default ColoringApplyPage;