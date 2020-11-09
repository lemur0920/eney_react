import React,{useState} from 'react';
import {FormGroup, makeStyles} from "@material-ui/core";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox"
import {withRouter} from "react-router-dom";
import {useDispatch} from "react-redux";
import {setAgree} from "../../modules/auth";

const useStyles = makeStyles(theme => ({
    checkBox: {
        padding:"0px 3px 0px 0px",
    },
}));


const AgreementCheckPage = ({location, history}) => {
    const classes = useStyles();
    const dispatch = useDispatch();

    const [checkAll, setCheckAll] = useState(false)

    const [check, setCheck] = useState({
        isTerms:false,
        isPersonal:false,
        isMarketing:false,
    })

    const allCheck = () =>{

        if(checkAll === false){
            setCheck({
                isTerms:true,
                isPersonal:true,
                isMarketing:true,
            })
        }else{
            setCheck({
                isTerms:false,
                isPersonal:false,
                isMarketing:false,
            })
        }

        setCheckAll(!checkAll);

    }

    const onSubmit = () =>{
        dispatch(setAgree(check));
        history.push("/auth/join");

    }

    return (
        <section className="sub_container">
            <div className="join_type_area">
                <h1 className="title_style_4">ENEY 회원가입</h1>
                <div className="join_agreement">
                    <div className="join_step">
                        <ul className="clfx">
                            <li className="on">
                                <div className="number">01</div>
                                <span className="txt">이용약관동의</span>
                            </li>
                            <li>
                                <div className="number">02</div>
                                <span className="txt">회원 정보 입력</span>
                            </li>
                            <li>
                                <div className="number">03</div>
                                <span className="txt">회원 가입 완료</span>
                            </li>
                        </ul>
                    </div>
                    <form onSubmit={(e) => {e.preventDefault();onSubmit();}}>
                    <div className="terms_all_agree">
                        <FormGroup row>
                            <input type='checkbox'
                                checked={checkAll}
                                // checked={state.checkedB}
                                onChange={allCheck}
                                value={true}
                                className={classes.checkBox}
                                color="primary"
                                id="all_chk"
                            />
                            <label htmlFor="all_chk">ENEY 전체 약관에 동의합니다.</label>
                        </FormGroup>
                    </div>
                    <div className="terms_list">
                        <FormGroup>
                        <ul>
                            <li>
                                <input type='checkbox'
                                    checked={check.isTerms}
                                    onChange={() => {setCheck({
                                        ...check,
                                        isTerms:!check.isTerms}
                                        ); setCheckAll(false)}}
                                    className={classes.checkBox}
                                    color="primary"
                                    id="terms_agree_1"
                                    required={true}
                                />
                                <label htmlFor="all_chk">ENEY 이용약관 동의(필수)</label>

                                {/*<input type="checkbox" id="terms_agree_1"/>*/}
                                {/*<label htmlFor="terms_agree_1">ENEY 이용약관 동의(필수)</label>*/}
                                <a href="#">이용약관 보기</a>
                            </li>
                            <li>
                                <input type='checkbox'
                                    checked={check.isPersonal}
                                    onChange={() => {setCheck({
                                        ...check,
                                        isPersonal:!check.isPersonal}
                                    ); setCheckAll(false)}}
                                    value={true}
                                    className={classes.checkBox}
                                    color="primary"
                                    id="terms_agree_2"
                                    required={true}
                                />
                                <label htmlFor="all_chk">개인 정보 수집 및 이용 동의(필수)</label>

                                {/*<input type="checkbox" id="terms_agree_2"/>*/}
                                {/*<label htmlFor="terms_agree_2">개인 정보 수집 및 이용 동의(필수)</label>*/}
                                <a href="#">개인 정보 수집 보기</a>
                            </li>
                            <li>
                                <input type='checkbox'
                                    checked={check.isMarketing}
                                    onChange={() => {setCheck({
                                        ...check,
                                        isMarketing:!check.isMarketing}
                                    ); setCheckAll(false)}}
                                    value={true}
                                    className={classes.checkBox}
                                    color="primary"
                                    id="terms_agree_3"
                                />
                                <label htmlFor="all_chk">홍보성 정보 수신 동의(선택)</label>

                                {/*<input type="checkbox" id="terms_agree_3"/>*/}
                                {/*<label htmlFor="terms_agree_3">홍보성 정보 수신 동의(선택)</label>*/}
                            </li>
                        </ul>
                        </FormGroup>
                    </div>

                    <div className="btn_area">
                        <button className="basic-btn03 btn-sky-bg">동의</button>
                    </div>
                    </form>

                </div>
            </div>
        </section>
    );
};

export default withRouter(AgreementCheckPage);