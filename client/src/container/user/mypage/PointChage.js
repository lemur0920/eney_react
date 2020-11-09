import React, {useEffect,createRef} from 'react';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import useStyles from './style'
import cx from "classnames";
import {useDispatch, useSelector} from "react-redux";
import {numberWithCommas} from "../../../lib/util/formatter"
import {epointItemInfo, payRequestInfo,setField,reset} from '../../../modules/payment/payment';
import PaymentForm from "../../../components/common/PaymentForm";
import {authUpdate} from "../../../modules/auth";
import pointImg from '../../../asset/image/point/point_img.png'
import swal from 'sweetalert';
import {withRouter} from "react-router-dom";

let stompClient

const PointChage = ({location, history}) => {
    const [price, setPrice] = React.useState("5000");
    const [VAT, setVAT] = React.useState("");
    const [totalPrice, setTotalPrice] = React.useState("");
    const [item, setItem] = React.useState(null);
    // const [objPopup, setObjPopup] = React.useState(null);


    const [paymentType, setPaymentType] = React.useState("credit");
    const classes = useStyles();
    const dispatch = useDispatch();

    let clientRef = createRef();

    let objPopup;

    const {epoint,itemList,payRequest} = useSelector(({mypage,payment,auth}) =>({
        epoint: auth.user.epoint,
        itemList : payment.item.epoint,
        payRequest: payment.payRequest
    }))

    useEffect( () => {

        const popup = objPopup
        dispatch(authUpdate());

        return () => {
            dispatch(reset());
            // stompClient.close();

        }
    },[])


    useEffect( () => {
        if(itemList === null){
            dispatch(epointItemInfo());
            // setPrice(parseInt(price))
            // setVAT(parseInt(price/10));
            // setTotalPrice(parseInt(price) + parseInt(price/10));

        }else{
            let selectItem = itemList.filter(function(item) {
                return item.price == parseInt(price)
            })
            setPrice(parseInt(price))
            setVAT(parseInt(price/10));
            setTotalPrice(parseInt(price) + parseInt(price/10));

            setItem(selectItem[0])
        }
    },[itemList])

    useEffect( () => {

        if(payRequest === null){
            return;
        }else{

            stompClient = require('../../../lib/util/socketConnect')

            stompClient.register([
                {route: `/queue/res/${payRequest.order_ID}`, callback: payResult},
            ]);

            let HForm = document.payment;
            HForm.target = "payment";

            //테스트 URL
            HForm.action = "http://tpay.billgate.net/credit/certify.jsp";
            //상용 URL
            // HForm.action = "https://pay.billgate.net/credit/certify.jsp";

            const option ="width=500,height=600,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,left=150,top=150";
            objPopup = window.open("", "payment", option);
            // setObjPopup(window.open("", "payment", option))

            if(objPopup == null){	//팝업 차단여부 확인
                swal("팝업이 차단되어 있습니다.\n팝업차단을 해제하신 뒤 다시 시도하여 주십시오.")
            }

            HForm.submit();
        }

    },[payRequest])

    const handleChange = event => {
        const value = event.target.value
        setPrice(parseInt(value))
        setVAT(value/10);
        setTotalPrice(parseInt(value) + parseInt(value/10));

        let selectItem = itemList.filter(function(item) {
            return item.price == value
        })

        setItem(selectItem[0])

    };

    const payResult = (msg) => {
        if(msg.body === 'success'){
            stompClient.close();
            swal("결제가 완료되었습니다").then(() => {
                history.push("/mypage?chargePage=1&paymentPage=1");
            })
        }else{
            stompClient.close();
            swal("결제가 실패되었습니다");
        }

        objPopup.close();
    }

    const getPayRequest = () =>{

        const itemInfo = {
            payWay:paymentType,
            service_code:item.service_code,
        }

        dispatch(payRequestInfo(itemInfo))

    }



    return (
        <div className={cx(classes.pointPayBox)}>
            <div className={cx(classes.registInfo)}>
                <div className='point_box'>
                    <img src={pointImg}/>
                    <div className='service_title_style_1'>
                        현재 보유 e-point
                    </div>
                    <div className='my_point'>
                        <span className='current_point'>
                            {numberWithCommas(epoint)}
                        </span>
                        <span className='point_txt'>
                            Point
                        </span>
                    </div>
                </div>
                <table>
                    <tbody>
                    <tr>
                        <td className={cx(classes.th)}>
                            충전 금액
                        </td>
                        <td className={cx(classes.td)}>
                            <FormControl >
                                <RadioGroup className={cx(classes.radioGroup)} aria-label="price" name="month" value={price} onChange={handleChange}>
                                    {itemList !== null && (
                                        itemList.map((item) =>
                                                (
                                                    <FormControlLabel key={item.service_code} value={item.price} control={<Radio color="primary"/>} label={item.service_name.substring(6,20)} />
                                                )
                                            )
                                        )
                                    }
                                </RadioGroup>
                            </FormControl>
                        </td>
                    </tr>
                    <tr>
                        <td className={cx(classes.th)}>
                            결제 수단
                        </td>
                        <td className={cx(classes.td)}>
                            <FormControl >
                                <RadioGroup className={cx(classes.radioGroup)} aria-label="type" name="type" value={paymentType}>
                                    <FormControlLabel  value="credit" control={<Radio  color="primary"/>} label="신용카드" />
                                </RadioGroup>
                            </FormControl>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div className={cx(classes.registPay)}>
                <div className="pay">
                    <div className="pay_div">
                        <h1>예상 견적 금액</h1>
                        <div className="amount_list">
                            <table>
                                <tbody>
                                <tr>
                                    <th>항목</th>
                                    <th>비용</th>
                                </tr>
                                <tr>
                                    <td>충전금액</td>
                                    <td className="charge_epoint">{numberWithCommas(price)}</td>
                                </tr>
                                <tr>
                                    <td>부가가치세</td>
                                    <td className="charge_vat">{numberWithCommas(VAT)}</td>
                                </tr>

                                <tr>
                                    <td id="payment">
                                        <span>결제금액</span>
                                    </td>
                                    <td>
                                        <span className="amount">{numberWithCommas(totalPrice)}</span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <a className="service_reg_next" onClick={() => {getPayRequest()}}>결제 </a>
                            {/*<div className="prev_service">*/}
                            {/*    <a className="service_reg_pre arrow" >이전단계</a>*/}
                            {/*    <a className="service_reg_pre">취소하기</a>*/}
                            {/*</div>*/}

                        </div>
                    </div>
                </div>
            </div>
            {payRequest !== null && (
                <PaymentForm payRequest={payRequest}/>
            )}

        </div>
    );
};

export default withRouter(PointChage);