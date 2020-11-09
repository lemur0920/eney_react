import React,{useState, useEffect} from 'react';
import {Box} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import CustomModal from "../../../../components/common/CustomModal";
import {useDispatch, useSelector} from "react-redux";
import {addCustomerGroup, getCustomerGroupList, initialize} from "../../../../modules/service/customer";
import {addCustomerCase} from "../../../../modules/admin/customer_case/customer_case";
import qs from "qs";

const CustomerGroupAddModal = ({open, onClose,cx}) => {

    const dispatch = useDispatch();

    const title = "신규 그룹 등록";
    const subTitle = "";

    const {result,error,loading} = useSelector(({customer,loading}) =>({
        result:customer.addResult,
        error:customer.addError,
        loading: loading['customer/ADD_CUSTOMER_GROUP'],
    }))


    const [group, setGroup] = useState({
        group_name:""
    })

    useEffect(() => {
        if(!loading && result && error === null){
            alert("그룹 등록 완료")
            onClose();
            const {page = 1} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            const data = {
                page: page
            }

            dispatch(getCustomerGroupList(data))
        }
    },[result,loading,error])

    const  onChangeValue = (e) => {
        const {name, value} = e.target;
        setGroup({
            ...group,
            [name]:value
        })

    }

    const handleAddGroup = () => {

        let form = new FormData(document.customerGroupAddForm);
        dispatch(addCustomerGroup(form))

    }

    const clearData = () =>{
        setGroup({
            group_name : ""
        });
        dispatch(initialize('addError'));
    }

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <Box>
                <div className={cx("customer_group_add_modal","tb_style_1")}>
                    <form onSubmit={(e) =>{e.preventDefault(); handleAddGroup();}} name="customerGroupAddForm">
                        <table>
                            <tbody>
                            <tr>
                                <td>그룹 이름</td>
                                <td><input type="text" required={true} value={group.group_name} name="group_name" onChange={(e) =>onChangeValue(e)}/></td>
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
                        <button type="submit" className={cx("btn","closeBtn")}>등록</button>
                        <button type="button" className={cx("btn","closeBtn")} onClick={(e)=>{onClose();clearData()}}>닫기</button>
                    </form>
                </div>
            </Box>
        </CustomModal>
    );
};

export default CustomerGroupAddModal;