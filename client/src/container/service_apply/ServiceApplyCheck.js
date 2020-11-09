import React,{useState} from 'react';
import {withRouter} from 'react-router-dom'
import useStyles from './style'
import cx from "classnames";
import Button from "../../components/common/Button";
import Moment from "moment";
import {numberWithCommas} from "../../lib/util/formatter";
import styled from "styled-components";
import palette from '../../lib/styles/palette'

const ApplyButton = styled(Button)`
    display: inline-block;
    margin:5px;
    padding: 9px 16px;
    width:250px;
`;

const CancelButton = styled(Button)`
    display: inline-block;
    margin:5px;
    background-color: white;
    color:${palette.cyan[0]};
    padding: 9px 16px;
    border: 1px solid ${palette.cyan[0]};
    width:250px;
    &:hover{
    background: white;
  }
`;

const ServiceApplyCheck = ({onPwCheck,service,history}) => {

    const classes = useStyles();

    console.log("####")
    console.log(service)
    console.log("####")

    const [payWay, setPayWay] = useState(() =>{
        switch(service.paymentType){
            case 'credit':
                return "신용카드"
            case 'epoint':
                return "포인트"
            case 'account_transfer':
                return "전자세금계산서(계좌이체)"
            case 'auto_transfer':
                return "전자세금계산서(자동이체)"
            case 'card_auto_transfer':
                return "신용카드 (자동이체)"
        }
    });


    return (
        <div className={cx(classes.box)}>
            <table className={cx(classes.table)}>
                <tbody>
                <tr>
                    <td className={cx(classes.th)}>
                        서비스 유형
                    </td>
                    <td className={cx(classes.td)}>
                         {service.item.service_name}
                    </td>
                </tr>
                <tr>
                    <td className={cx(classes.th)}>
                        과금 기준
                    </td>
                    <td className={cx(classes.td)}>
                        {service.item !== null && (service.item.charge_basis)}
                    </td>
                </tr>
                <tr>
                    <td className={cx(classes.th)}>
                        약정기간
                    </td>
                    <td className={cx(classes.td)}>
                        {parseInt(service.month) !== 0 ? (`${service.month} 개월`) : ("-")}
                    </td>
                </tr>
                <tr>
                    <td className={cx(classes.th)}>
                        약정만기일
                    </td>
                    <td className={cx(classes.td)}>
                        {parseInt(service.month) !== 0 ? (`${Moment().add(service.month, "M").format(Moment.HTML5_FMT.DATE)} 개월`) : ("해지 시 까지")}
                    </td>
                </tr>
                {/*<tr>*/}
                {/*    <td className={cx(classes.th)}>*/}
                {/*        청구방법*/}
                {/*    </td>*/}
                {/*    <td>*/}

                {/*    </td>*/}
                {/*</tr>*/}
                <tr>
                    <td className={cx(classes.th)}>
                        결제방법
                    </td>
                    <td className={cx(classes.td)}>
                        {payWay}
                        <br/>
                        <a  className={cx(classes.downloadBtn)} href="https://drive.google.com/file/d/0B8qNetmUf_hLTjlYcmk4SWlVc1U/view" target="_blank">자동이체 신청서 다운로드</a>
                    </td>
                </tr>
                <tr>
                    <td className={cx(classes.th)}>
                        이용목적
                    </td>
                    <td className={cx(classes.td)}>
                        {service.purpose}
                    </td>
                </tr>
                <tr>
                    <td className={cx(classes.th)}>
                        결제금액(VAT포함)
                    </td>
                    <td className={cx(classes.td)}>
                        {numberWithCommas(service.totalPrice)}
                    </td>
                </tr>
                </tbody>
            </table>
            <p className="font_bold">전자세금계산서는 매월 15일에 발행됩니다.</p>
            <p>청구서에 나와있는 계좌번호로 납부하여 주시거나, 자동이체를 원하시는 고객은 Fax(070-7933-1616)으로 자동이체 신청서를 작성하셔서 보내주시기 바랍니다.</p><br/>
            <div className={cx(classes.applyBtnBox)}>
                <ApplyButton eney onClick={onPwCheck}>신청</ApplyButton>
                <CancelButton onClick={() => {history.push("/price")}}>취소</CancelButton>
            </div>
        </div>
    );
};

export default withRouter(ServiceApplyCheck);