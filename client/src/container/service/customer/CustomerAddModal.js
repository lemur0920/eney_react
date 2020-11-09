import React,{useState,useEffect} from 'react';
import {Box} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {withRouter} from 'react-router-dom';
import CustomModal from "../../../components/common/CustomModal";
import {isNumber} from "../../../lib/util/formatter"
import {useDispatch, useSelector} from "react-redux";
import {getCustomerInfo,addCustomer,getCustomerList,initialize,setError} from "../../../modules/service/customer";
import CustomerEventTable from "../../../components/service/customer/CustomerEventTable";
import qs from "qs";


const CustomerAddModal = ({open, onClose,cx}) => {

    const dispatch = useDispatch();
    const [customer, setCustomer] = useState({
        name:"",
        gender:"",
        address:"",
        team_name:"",
        email:"",
        phone_number:"",
    });

    const title = "신규고객 추가";
    const subTitle = "";
    const style = {
        maxWidth:'500',
    }

    const {error,result,loading} = useSelector(({customer,loading}) =>({
        result:customer.customerAddResult,
        error:customer.customerAddError,
        loading: loading['customer/ADD_CUSTOMER'],
    }))


    useEffect(() => {
        return () =>{
            dispatch(initialize("customerAddResult"))
            dispatch(initialize("customerAddError"))
        }
    },[])


    useEffect(() => {
        if(!loading && result){
            alert("고객 정보가 추가되었습니다")
            onClose();

            const {page = 1,search_filed = null, search = null, startDate = null, endDate = null} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page,
                search_filed : search_filed,
                search : search,
                startDate: startDate,
                endDate : endDate
            }

            dispatch(getCustomerList(data))
        }
    },[result,loading])


    const onChangeValue = (e) => {
        const {name, value} = e.target;
        setCustomer({
            ...customer,
            [name]:value
        })
    }


    const handleAdd = () => {

        if(isNumber(customer.phone_number)){
            dispatch(addCustomer(customer))
        }else{
            dispatch(setError({
                key:"customerAddError",
                value:"숫자만 입력이 가능합니다"
            }))

        }

    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} style={style}>
            <div className={cx("popup","customer_add_popup")}>
                <div className={cx("popup-content")}>
                    <h1>고객관리</h1>
                    <div className={cx("inner")}>
                        <div className={cx("tb_scroll")}>
                            <div className={cx("tb_style_2")}>
                                <form onSubmit={(e) =>{e.preventDefault();handleAdd(); }} name="customerInfoEditForm">
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>이름</td>
                                            <td>
                                                <input type="text" required={true} value={customer.name} name="name" onChange={(e) =>onChangeValue(e)}/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>번호</td>
                                            <td>
                                                <input type="text" required={true} value={customer.phone_number} name="phone_number" onChange={(e) =>onChangeValue(e)}/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>성별</td>
                                            <td>
                                                <select name="gender" onChange={(e) =>onChangeValue(e)}>
                                                    <option value="M">남자</option>
                                                    <option value="W">여자</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>지역</td>
                                            <td>
                                                <input type="text" required={false} value={customer.address} name="address" onChange={(e) =>onChangeValue(e)}/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>소속</td>
                                            <td>
                                                <input type="text" required={false} value={customer.team_name} name="team_name" onChange={(e) =>onChangeValue(e)}/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>이메일</td>
                                            <td>
                                                <input type="email" required={false} value={customer.email} name="email" onChange={(e) =>onChangeValue(e)}/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colSpan={2} style={{border:"none"}}>
                                                {!result && error !== null && (
                                                    <Typography color="error">{error}</Typography>
                                                )}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div style={{textAlign:"center"}}>
                                        <button type="submit" className={cx("btn")}>추가</button>
                                    </div>
                                    {/*<button type="button" className={cx("btn","closeBtn")} onClick={(e)=>{onClose();clearData()}}>닫기</button>*/}
                                </form>
                            </div>
                        </div>
                        <div className={cx("popup_close")}>
                            <button onClick={() => onClose()}><span></span></button>
                        </div>
                    </div>
                </div>
            </div>
        </CustomModal>
    );
};

export default withRouter(CustomerAddModal);