import React from 'react';

const JoinTemplate = ({children}) => {
    return (
        <div>
            <section className="sub_container">
                <div className="join_type_area">
                    <h1 className="title_style_4">ENEY 회원가입</h1>
                    <div className="join_agreement">
                        <div className="join_step">
                            <ul className="clfx">
                                <li>
                                    <div className="number">01</div>
                                    <span className="txt">이용약관동의</span>
                                </li>
                                <li className="on">
                                    <div className="number">02</div>
                                    <span className="txt">회원 정보 입력</span>
                                </li>
                                <li>
                                    <div className="number">03</div>
                                    <span className="txt">회원 가입 완료</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    {children}
                </div>
            </section>
        </div>
    );
};

export default JoinTemplate;