import React from 'react';
import PointChage from "../../container/user/mypage/PointChage";

const PointChargePage = () => {
    return (
        <section className={`sub_container`}>
            <div className="title_cont">
                <h1 className="title_style_5">E-포인트 충전하기</h1>
            </div>
            <PointChage/>
        </section>
    );
};

export default PointChargePage;