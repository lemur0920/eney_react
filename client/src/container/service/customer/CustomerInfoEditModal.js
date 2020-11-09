import React,{useState,useEffect} from 'react';
import {Box} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {withRouter} from 'react-router-dom';
import CustomModal from "../../../components/common/CustomModal";
import {useDispatch, useSelector} from "react-redux";
import {getCustomerInfo,updateCustomerInfo,getCustomerList,initialize} from "../../../modules/service/customer";
import CustomerEventTable from "../../../components/service/customer/CustomerEventTable";
import qs from "qs";


const CustomerInfoEditModal = ({open, onClose,cx,customerIdx}) => {

    const dispatch = useDispatch();
    const [customerInfo, setCustomerInfo] = useState(null);

    const title = "고객정보 수정";
    const subTitle = "";
    const style = {
        maxWidth:'500',
    }

    const {customer,error,result,loading,updateLoading} = useSelector(({customer,loading}) =>({
        customer:customer.customerInfo,
        result:customer.infoEditResult,
        error:customer.error,
        loading: loading['customer/GET_CUSTOMER_INFO'],
        updateLoading: loading['customer/UPDATE_CUSTOMER_INFO'],
    }))


    useEffect(() => {
        return () =>{
            dispatch(initialize("infoEditResult"))
        }
    },[])

    useEffect(() => {
        const idx = {
            idx:customerIdx
        }
        dispatch(getCustomerInfo(idx))

    },[customerIdx])

    useEffect(() => {
        if(!loading && customer !== null && error === null){
            setCustomerInfo(customer)
        }
    },[customer,loading,error])

    useEffect(() => {
        if(!updateLoading && result){
            alert("고객 정보가 수정되었습니다")
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
    },[result,updateLoading])


    const onChangeValue = (e) => {
        const {name, value} = e.target;
        setCustomerInfo({
            ...customerInfo,
            [name]:value
        })

    }


    const handleInfoUpdate = () => {

        let form = new FormData(document.customerInfoEditForm);
        dispatch(updateCustomerInfo(form))

    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle} style={style}>
            <div className={cx("popup","customer_info_edit_popup")}>
                <div className={cx("popup-content")}>
                    <h1>고객관리</h1>
                    <div className={cx("inner")}>
                        <div className={cx("tb_scroll")}>
                            <div className={cx("tb_style_2")}>
                                        {customerInfo !== null && (
                                            <form onSubmit={(e) =>{e.preventDefault(); }} name="customerInfoEditForm">
                                                <table>
                                                    <tbody>
                                                    <tr>
                                                        <td>이름</td>
                                                        <td>
                                                            <input type="text" required={true} value={customerInfo.name === null ? "" : customerInfo.name} name="name" onChange={(e) =>onChangeValue(e)}/>
                                                            <input type="number" required={true} value={customerInfo.idx !== null ? customerInfo.idx : 0} name="idx" onChange={(e) =>onChangeValue(e)} hidden={true}/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>소속</td>
                                                        <td>
                                                            <input type="text" required={true} value={customerInfo.team_name !== null ? customerInfo.team_name : ""} name="team_name" onChange={(e) =>onChangeValue(e)}/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>이메일</td>
                                                        <td>
                                                            <input type="email" required={true} value={customerInfo.email !== null ? customerInfo.email : ""} name="email" onChange={(e) =>onChangeValue(e)}/>
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
                                                    <button type="submit" className={cx("btn")} onClick={() => handleInfoUpdate()}>수정</button>
                                                </div>
                                                {/*<button type="button" className={cx("btn","closeBtn")} onClick={(e)=>{onClose();clearData()}}>닫기</button>*/}
                                            </form>

                                        )}
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

export default withRouter(CustomerInfoEditModal);