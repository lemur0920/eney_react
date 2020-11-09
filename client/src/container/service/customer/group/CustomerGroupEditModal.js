import React,{useState, useEffect} from 'react';
import {Box} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import CustomModal from "../../../../components/common/CustomModal";
import {useDispatch, useSelector} from "react-redux";
import {updateCustomerGroup,getCustomerGroupList, initialize} from "../../../../modules/service/customer";
import qs from "qs";

const CustomerGroupEditModal = ({open, onClose,cx,editGroup}) => {

    const dispatch = useDispatch();

    const title = "그룹 수정";
    const subTitle = "";

    const {result,error,loading} = useSelector(({customer,loading}) =>({
        result:customer.updateResult,
        error:customer.updateError,
        loading: loading['customer/UPDATE_CUSTOMER_GROUP'],
    }))


    const [group, setGroup] = useState({
        idx:null,
        group_name:""
    })

    useEffect(() => {
        setGroup({
            idx:editGroup.idx,
            group_name:editGroup.group_name
        })

    },[editGroup])

    useEffect(() => {
        if(!loading && result && error === null){
            alert("그룹 수정 완료")
            onClose();
            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page
            }

            dispatch(getCustomerGroupList(data))
        }

        if(!loading && !result && error !== null){
            alert("요청중 에러가 발생했습니다");
        }
    },[result,loading,error])

    const  onChangeValue = (e) => {
        const {name, value} = e.target;
        setGroup({
            ...group,
            [name]:value
        })

    }

    const handleUpdateGroup = () => {

        let form = new FormData(document.customerGroupUpdateForm);
        dispatch(updateCustomerGroup(form))

    }

    const clearData = () =>{
        setGroup({
            idx:null,
            group_name:""
        });
        dispatch(initialize('updateError'));
    }

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <Box>
                <div className={cx("customer_group_add_modal","tb_style_1")}>
                    <form onSubmit={(e) =>{e.preventDefault(); handleUpdateGroup();}} name="customerGroupUpdateForm">
                        <table>
                            <tbody>
                            <tr>
                                <td>그룹 이름</td>
                                <td>
                                    <input type="text" required={true} value={group.group_name !== null ? group.group_name : ""} name="group_name" onChange={(e) =>onChangeValue(e)}/>
                                    <input type="number" required={true} value={group.idx !== null ? group.idx : 0} name="idx" onChange={(e) =>onChangeValue(e)} hidden={true}/>
                                </td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    {!result && error !== null && (
                                        <Typography color="error">{error}</Typography>
                                    )}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <button type="submit" className={cx("btn","closeBtn")}>수정</button>
                        <button type="button" className={cx("btn","closeBtn")} onClick={(e)=>{onClose();clearData()}}>닫기</button>
                    </form>
                </div>
            </Box>
        </CustomModal>
    );
};

export default CustomerGroupEditModal;