import React, {Fragment, useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core";
import Certification from "../util/Certification";
import Postcode from "../util/Postcode";
import CustomModal from "../common/CustomModal";
import {changeField, check, initializeForm} from "../../modules/auth";

const useStyles = makeStyles(theme => ({
    input: {
        color:"black",
    },
}));

const JoinForm = ({certifyForm, onSubmit,onDuplicateCheck,authError, registerCheck, certifySuccessNum,certifySuccessName, maxLengthCheck, userType,moveBack}) => {
    const classes = useStyles();

    const [certifiIsOpen,setCertifiIsOpen] = useState(false);
    const [show, setShow] = useState(false);
    const [address, setAddress] = useState({
        postCode:"",
        address:"",
        addressDetail:""
    });

    const [form, setForm] = useState({
        userid: '',
        corporate_number:'',
        company_name:'',
        birth_day:'',
        name:'',
        ci:'',
        di:'',
        phone_number:'',
        email:'',
        password:'',
        password_check:'',
        corporate_number01:"",
        corporate_number02:"",
        corporate_number03:"",
        postCode:"",
        address:"",
        addressDetail:"",
        member_kind:"",
        marketing_agree:false
    });

    useEffect(() => {
        setForm({
            ...form,
            name:certifyForm.certifySuccessName,
            phone_number:certifyForm.certifySuccessNum,
            birth_day:certifyForm.birth_day,
            ci:certifyForm.ci,
            di:certifyForm.di,
        })

    },[certifyForm])

    useEffect(() => {
        setForm({
            ...form,
            member_kind:userType,
        })

    },[userType])



    const onChange = e => {
        const {value, name} = e.target;
        setForm({
            ...form,
            [name]:value
        })
    };

    const toglleModal = () =>{
        setCertifiIsOpen(!certifiIsOpen)
    }

    const showMap = () =>{
        setShow(!show)
    }

    const onAddrChange = (e) =>{

        const {value, name} = e.target;

        setAddress({
            ...address,
            [name]:value
        })
    }


    const getAddress = (data) => {
        let fullAddress = data.address;
        let extraAddress = '';

        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }

        setForm({
            ...form,
            address:fullAddress,
            postCode:data.postcode
        })

    }

    return (
        <form onSubmit={(e) => {e.preventDefault(); onSubmit(form)}}>
            <div className="join_form">
                <ul>
                    {userType === "corporate" &&
                    <Fragment>
                        <li>
                            <span className="title">기업명<b>*</b></span>
                            <div className="input_area">
                                <input type="text" name="company_name" value={form.company_name} onChange={onChange} required={userType === "corporate"}/>
                            </div>
                        </li>
                        <li>
                            <span className="title">사업자등록번호<b>*</b></span>
                            <div className="input_area">
                                <input className="license_num" type="number" name="corporate_number01" value={form.corporate_number01} onChange={onChange} maxLength="3" onInput={maxLengthCheck} required={userType === "corporate"}/>
                                &#45;
                                <input className="license_num" type="number" name="corporate_number02" value={form.corporate_number02} onChange={onChange} maxLength="2" onInput={maxLengthCheck} required={userType === "corporate"}/>
                                &#45;
                                <input className="license_num" type="number" name="corporate_number03" value={form.corporate_number03} onChange={onChange} maxLength="5" onInput={maxLengthCheck} required={userType === "corporate"}/>
                                {/*<button className="input_btn btn-gray-bg" >확인</button>*/}
                            </div>
                            {!registerCheck.check.result && registerCheck.check.msg.corporateError !== "" &&(
                                <div className="alert_validation">
                                    <span>
                                        {registerCheck.check.msg.corporateError}
                                    </span>
                                </div>
                            )}
                        </li>
                    </Fragment>
                    }
                    <li>
                        <span className="title">아이디<b>*</b></span>
                        <div className="input_area">
                            <input type="text" name="userid" value={form.userid} onChange={onChange} required={true}/>
                            {!registerCheck.check.result && registerCheck.check.msg.userIdError !== "" &&(
                                <div className="alert_validation">
                                    <span>
                                        {registerCheck.check.msg.userIdError}
                                    </span>
                                </div>
                            )}
                            {registerCheck.check.result && registerCheck.check.msg.userIdError === "" &&(
                                <div>
                                <span>
                                    사용가능한 아이디입니다
                                </span>
                                </div>
                            )}
                            <button type="button" onClick={ () => onDuplicateCheck(form.userid)} className="input_btn btn-gray-bg">중복 확인</button>
                        </div>
                    </li>
                    <li>
                        <span className="title">이메일<b>*</b></span>
                        <div className="input_area">
                            <input type="email" name="email" value={form.email} onChange={onChange} required={true} noValidate/>
                            {!registerCheck.check.result && registerCheck.check.msg.emailError !== "" &&(
                                <div className="alert_validation">
                                <span>
                                    {registerCheck.check.msg.emailError}
                                </span>
                                </div>
                            )}
                        </div>
                    </li>
                    <li>
                        <span className="title">비밀번호<b>*</b></span>
                        <div>
                            <input type="password" name="password" value={form.password} onChange={onChange} required={true} />
                            {!registerCheck.check.result && registerCheck.check.msg.passwordError !== "" &&(
                                <div className="alert_validation">
                                <span>
                                    {registerCheck.check.msg.passwordError}
                                </span>
                                </div>
                            )}
                        </div>
                        <div><input type="password" name="password_check" value={form.password_check} onChange={onChange} required={true}/></div>
                    </li>
                    <li>
                        <span className="title">이름<b>*</b></span>
                        <div>
                            <input type="text" name="name" value={certifyForm.certifySuccessName} readOnly={true} required={true} onClick={toglleModal} placeholder="본인 인증을 완료해주세요"/>
                            {!certifyForm.isCertify && certifyForm.authError !== "" &&(
                                <div className="alert_validation">
                                <span>
                                    {certifyForm.authError}
                                </span>
                                </div>
                            )}
                        </div>
                    </li>
                    <li>
                        <span className="title">핸드폰 번호<b>*</b></span>
                        <div className="input_area w_120">
                            <input type="number" maxLength="11" name="phone_number" value={certifyForm.certifySuccessNum} onInput={maxLengthCheck} readOnly={true} required={true} onClick={toglleModal}/>
                            <button className="input_btn btn-gray-bg" type="button" onClick={toglleModal}>인증번호 요청</button>
                        </div>
                    </li>
                    <li>
                        <span className="title">주소</span>
                        <div className="input_area">
                            <input type="text" placeholder='우편번호' value={form.postCode} readOnly={true} required={true}/>
                            <button type="button" onClick={showMap} name="postCode" className="input_btn btn-gray-bg">주소검색</button>
                        </div>
                        <div>
                            <input placeholder='주소' name="address" value={form.address} type="text" readOnly={true} required={true}/>
                        </div>
                        <div>
                            <input placeholder='상세주소' name="addressDetail" value={form.addressDetail} onChange={onChange} type="text"/>
                        </div>
                    </li>
                </ul>
            </div>
            <div className="join_form_btn clfx">
                <div className="left_btn"><a href="#"  onClick={moveBack} className="basic-btn03 btn-gray-bg btn_prev"><span>이전단계</span></a>
                </div>
                <div className="right_btn">
                    <button href="#" className="basic-btn03 btn-sky-bg">가입완료</button>
                </div>
            </div>
            {certifiIsOpen && (
                <Certification onClose={toglleModal} type="JOIN"/>
            )}
            <CustomModal open={show} onClose={showMap}>
                <Postcode getAddress={getAddress} onClose={showMap}/>
            </CustomModal>
        </form>


    );
};

export default JoinForm;