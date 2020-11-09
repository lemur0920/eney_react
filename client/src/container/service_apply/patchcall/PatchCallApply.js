import React,{useEffect,useState,useRef,Fragment} from 'react';
import {numberWithCommas} from "../../../lib/util/formatter";
import FormControl from "@material-ui/core/FormControl";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import {getItemList,registPatchcall,reset} from "../../../modules/service_apply/serviceAppy";
import {pwCheck,initializeForm} from "../../../modules/auth";
import useStyles from './style'
import cx from "classnames";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from 'react-router-dom'
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import PasswordCheckModal from "../PasswordCheckModal";
import ServiceApplyCheck from "../ServiceApplyCheck";
import Moment from "moment";
import swal from 'sweetalert';

const PatchCallApply = ({location, match,history}) => {

    let objPopup;
    // let stompClient = require('../../../lib/util/socketConnect')

    const purposeArray = ["O2O플랫폼","온라인마케팅","기타"]


    const [showCheck, setShowCheck] = useState(false);
    const [error, setError] = useState({
        file:null
    });
    const [file, setFile] = useState(null);

    const [numberAmount, setNumberAmount] = useState( () => {
        switch(match.params.type){
            case 'single':
                return 10
            case 'double':
                return 100
            case 'support':
                return 300
            case 'start-up':
                return 500
            case 'business':
                return 1000
            case 'enterprise':
                return 2000
            default:
                return 0
        }}
    );



    const [pwCheckIsOpen, setPwCheckIsOpen] = useState(false);

    const [service, setService] = useState({
        itemList:null,
        paymentType:'',
        month: 12,
        price:"5000",
        VAT:"",
        totalPrice:"",
        item:null,
        category:"",
        install_cost:0,
        purposeSelect:purposeArray[0],
        purpose:purposeArray[0],
        end_date: new Date()
    });
    const [formData, setFormData] = useState(null);

    const classes = useStyles();
    const dispatch = useDispatch();
    // const serviceApplyForm = useRef()

    const {items,pwCheckResult,pwCheckError,email,serviceRegistResult,serviceRegistError} = useSelector(({serviceApply,auth}) =>({
        items: serviceApply.service.items,
        pwCheckResult:auth.pwCheck.isSuccess,
        pwCheckError:auth.pwCheck.error,
        email:auth.user.email,
        // payRequest: payment.payRequest,
        // payMethod: payment.payMethod,
        serviceRegistResult:serviceApply.serviceRegist.patchcall.isSuccess,
        serviceRegistError:serviceApply.serviceRegist.patchcall.error
    }))

    useEffect(() => {
        dispatch(getItemList(match.params.service));
        dispatch(reset());

        return () => {
            dispatch(reset());

        }
    },[])

    useEffect(() => {
        if(items !== undefined && items.length > 0){
            console.log(items)
            console.log(match.params.type)
            const list =  items.filter(item => item.service_type.toUpperCase() === match.params.type.toUpperCase());

            const defaultItem = list[0];

            setService({
                ...service,
                month:defaultItem.service_period,
                price:parseInt(defaultItem.price),
                VAT:defaultItem.price/10,
                totalPrice:parseInt(defaultItem.price) + parseInt(defaultItem.price/10) + parseInt(defaultItem.install_cost),
                item:defaultItem,
                install_cost: parseInt(defaultItem.install_cost),
                paymentType:"account_transfer",
                itemList:list,
                category:defaultItem.category
            })
        }

    },[items])

    useEffect(() => {
        if(pwCheckResult && pwCheckError === null){
            toggleModal();

            let form = new FormData(formData);
            dispatch(registPatchcall(form))
        }

    },[pwCheckResult])


    //서비스 신청 결과
    useEffect( () => {


        if(serviceRegistResult && serviceRegistError === null){
            swal("신청이 완료되었습니다. 자세한 내용은 마이페이지에서 확인 가능합니다.");
            dispatch(reset());
            history.push("/")
        }
    },[serviceRegistResult])



    const changePurpose = (e) =>{
        let {name, value} = e.target

        if(value === "기타"){

            setService({
                ...service,
                purposeSelect:value,
                purpose:""
            })

            return
        }

        if(name === "purpose"){
            setService({
                ...service,
                purpose:value
            })
        }else{
            setService({
                ...service,
                purposeSelect:value,
                purpose:value
            })
        }


    }


    const handleChange = event => {
        const value = event.target.value

        const item = service.itemList.filter(item => item.service_period === value)[0]

        setService({
            ...service,
            month:value,
            price:parseInt(item.price),
            VAT:item.price/10,
            totalPrice:parseInt(item.price) + parseInt(item.price/10),
            item:item,
        })

    };

    const handlePaymentType = event => {
        const value = event.target.value
        // setPaymentType(value)

        setService({
            ...service,
            paymentType:value
        })

    };

    const toggleModal = () =>{
        dispatch(initializeForm('pwCheck'))
        setPwCheckIsOpen(!pwCheckIsOpen)
    }

    const handlePwCheck = (pw) =>{
        console.log(pw)

        dispatch(pwCheck(pw));
    }

    const moveCheck = () => {
        // if(file === null){
        //     setError({file:"사업자등록증을 추가하세요"})
        //     return;
        // }
        setFormData(document.serviceApplyForm);
        setShowCheck(true);
    }

    return (
        <Fragment>
            {!showCheck ? (
                <div className={cx(classes.pointPayBox)}>
                    <div className={cx(classes.registInfo)}>
                        <form  name='serviceApplyForm' onSubmit={(e) => e.preventDefault()}>
                            <table>
                                <tbody>
                                <tr>
                                    <td className={cx(classes.th)}>
                                        서비스 유형
                                    </td>
                                    <td className={cx(classes.td)}>
                                        { service.item !== null && (service.item.service_name)}
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
                                        이용 기간
                                    </td>
                                    <td className={cx(classes.td)}>
                                        <FormControl >
                                            <RadioGroup className={cx(classes.radioGroup)} aria-label="price" name="service_period" value={service.month} onChange={handleChange}>
                                                {service.itemList != null && (
                                                    service.itemList.map((item) =>
                                                        (
                                                            <FormControlLabel key={item.service_code} value={item.service_period} control={<Radio color="primary"/>} label={parseInt(item.service_period) !== 0 ? (`${item.service_period} 개월`) : ("해지 시 까지")} />
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
                                            <RadioGroup className={cx(classes.radioGroup)} aria-label="type" name="pay_way" value={service.paymentType} onChange={handlePaymentType}>
                                                <FormControlLabel  className={cx(classes.radioLabel)} value="account_transfer" control={<Radio  color="primary"/>} label="전자세금계산서(계좌이체)" />
                                                <FormControlLabel  className={cx(classes.radioLabel)} value="auto_transfer" control={<Radio  color="primary"/>} label="전자세금계산서(자동이체)" />
                                                <FormControlLabel  className={cx(classes.radioLabel)} value="card_auto_transfer" control={<Radio  color="primary"/>} label="신용카드 (자동이체)" />
                                            </RadioGroup>
                                        </FormControl>
                                    </td>
                                </tr>
                                <tr>
                                    <td className={cx(classes.th)}>
                                        이용 목적
                                    </td>
                                    <td className={cx(classes.td)}>
                                        {service.purposeSelect === "기타" && (
                                            <TextField
                                                name="patchcall_purpose"
                                                value={service.purpose}
                                                onChange={changePurpose}
                                                InputLabelProps={{
                                                    shrink: true,
                                                }}
                                            />
                                        )}

                                        <FormControl >
                                            <Select
                                                name="patchcall_purpose"
                                                value={service.purposeSelect}
                                                onChange={changePurpose}
                                            >
                                                {purposeArray.map((item, index) => (
                                                    <MenuItem  key={item} value={item}>{item}</MenuItem>
                                                ))
                                                }
                                            </Select>
                                        </FormControl>
                                    </td>
                                </tr>
                                {/*<tr>*/}
                                {/*    <td className={cx(classes.th)}>*/}
                                {/*        사업자등록증*/}
                                {/*    </td>*/}
                                {/*    <td className={cx(classes.td)}>*/}
                                {/*        <input type='file' name='files' onChange={(e) => setFile(e.target.value)} accept=".jpg, .jpeg, .png, .pdf" />*/}
                                {/*        {error.file !== null && (*/}
                                {/*            <Typography color="error">{error.file}</Typography>*/}
                                {/*        )}*/}
                                {/*    </td>*/}
                                {/*</tr>*/}
                                </tbody>
                            </table>

                            {/*<input name="end_date" value={Moment.add('month',1).format(Moment.HTML5_FMT.DATE)} readOnly={true}/>*/}
                            <input name="end_date" value={Moment().add(service.month, "M").format(Moment.HTML5_FMT.DATE)} readOnly={true} hidden={true}/>
                            <input name="service_vat" value={service.VAT} readOnly={true} hidden={true}/>
                            <input name="service_amount" value={parseInt(service.month) !== 0 ? (service.price / service.month) : (service.price)} readOnly={true} hidden={true}/>
                            <input name="number_amount" value={numberAmount} readOnly={true} hidden={true}/>
                            <input name="p_service" value="API" readOnly={true} hidden={true}/>
                            <input name="service_type" value={match.params.type} readOnly={true} hidden={true}/>
                            <input name="publish_email" value={email} readOnly={true} hidden={true}/>
                            <input name="category" value={service.category} readOnly={true} hidden={true}/>
                        </form>
                    </div>
                    <div className={cx(classes.registPay)}>
                        <div className="pay">
                            <div className="pay_div">
                                <h1>예상 견적 금액</h1>
                                <p className="txt_style_1">(약정기간 청구되는 금액이며 청구는 매월이용금액기준으로 진행됩니다.)</p>
                                <div className="amount_list">
                                    <table>
                                        <tbody>
                                        <tr>
                                            <th>항목</th>
                                            <th>비용</th>
                                        </tr>
                                        <tr>
                                            <td>이용료</td>
                                            <td className="charge_epoint">{numberWithCommas(service.price)}</td>
                                        </tr>
                                        {parseInt(service.install_cost) !== 0 && (
                                            <tr>
                                                <td>설치비</td>
                                                <td className="">{numberWithCommas(service.install_cost)}</td>
                                            </tr>

                                        )}
                                        <tr>
                                            <td>부가가치세</td>
                                            <td className="charge_vat">{numberWithCommas(service.VAT)}</td>
                                        </tr>
                                        <tr>
                                            <td id="payment">
                                                <span>결제금액</span>
                                            </td>
                                            <td>
                                                <span className="amount">{numberWithCommas(service.totalPrice)}</span>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <a className="service_reg_next" onClick={moveCheck}>다음 </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            ) : (<ServiceApplyCheck onPwCheck={toggleModal} service={service}/>)}
            <PasswordCheckModal open={pwCheckIsOpen} onClose={toggleModal} onSubmit={handlePwCheck} error={pwCheckError}/>
            {/*{payRequest !== null && (*/}
            {/*    <PaymentForm payRequest={payRequest}/>*/}
            {/*)}*/}
        </Fragment>
    );
};

export default withRouter(PatchCallApply);