import React, {Fragment, useCallback, useEffect,useState} from 'react';
import PatchcallManagementTable from "../../../components/service/patchcall/PatchcallManagementTable";
import Pagination from "../../../components/util/Pagination";
import Loading from "../../../components/util/Loading";
import {useDispatch, useSelector} from "react-redux";
import {withRouter} from "react-router-dom";
import qs from "qs";
import {getAgent,changeAgentField,changeAddressField,disableCallback,updateAgent,initialize} from "../../../modules/service/patchcall";
import PatchcallDetailForm from "../../../components/service/patchcall/PatchcallDetailForm";
import styled from "styled-components";
import Button from "../../../components/common/Button";
import CustomAlertModal from "../../../components/common/CustomAlertModal";

const CustomCancelButton = styled(Button)`
    padding: 6px 16px;
    font-size: 0.875rem;
    margin: 8px;
    background-color: white;
    color:#37afe5;
    border:1px solid #37afe5;
    font-weight: 500;
    line-height: 1.75;
    &:hover{
       background: none;
       }
`;

const PatchCallManagementDetail = ({cx,history}) => {


    const dispatch = useDispatch();
    const [isOpen, setIsOpen] = useState(false);
    const [address, setAddress] = useState({
        zonecode:"",
        address:"",
        detailAddress:""
    });

    const {patchcall,agentAddress,updateResult,updateError,fileUploadError, loading} = useSelector(({patchcall,loading}) =>({
        patchcall: patchcall.agent,
        agentAddress:patchcall.address,
        updateResult : patchcall.updateResult,
        updateError : patchcall.updateError,
        fileUploadError : patchcall.fileUploadError,
        loading: loading['patchcall/GET_AGENT'],
    }))

    useEffect(() => {
        const {vno} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        dispatch(getAgent(vno))

        return () =>{
            dispatch(initialize())
        }

    },[])


    useEffect(() => {
        if(updateResult && updateError === null && fileUploadError === null){
            setIsOpen(true)
        }

    },[updateResult])

    useEffect(() => {
        if(!loading){
            setAddress({
                ...address,
                ...agentAddress
            })
        }

    },[loading,agentAddress])

    const onChangeAgentValue = useCallback(data =>{
        const {value, key} = data;
        dispatch(
            changeAgentField({
                key:key,
                value
            })
        )
    },[])

    const onChangeAgentAddr = (e) => {
        const {name, value} = e.target;
        setAddress({
            ...address,
            [name]:value
        })

    }


    const onDisableCallback = () => {
        dispatch(disableCallback());
    }

    const onUpdate = () => {
        const data = {
            agent: patchcall.agentVO,
            address:address
        }
        dispatch(updateAgent(data))
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

        console.log(data)
        setAddress({
            ...address,
            ...data
        })
    }



    return (
        <Fragment>
            <div className={cx("navi")}>
                <ul className="clfx">
                    <li>패치콜</li>
                    <li>번호관리</li>
                    <li>번호설정변경</li>
                </ul>
            </div>

            <div className={cx("box_cont")}>
                <div className={cx("agent_detail")}>

                    <div className={cx("title_area")}>
                        <h1 className={cx("title_style_2")}>번호설정변경</h1>
                    </div>
                    <div className={cx("tb_style_3")}>
                        {patchcall !== null && !loading ? (
                            <Fragment>
                                <PatchcallDetailForm agent={patchcall.agentVO} address={address} getAddress={getAddress} callbackCheck={patchcall.callbackCheck} messagingList={patchcall.messagingList} cx={cx} onChangeAgentValue={onChangeAgentValue} onChangeAgentAddr={onChangeAgentAddr} onDisableCallback={onDisableCallback}/>
                                <div className={cx("btn_box")}>
                                    <Button eney className={cx("submit_btn")} onClick={(e) => { onUpdate() }}>변경</Button>
                                    <CustomCancelButton type="button" className={cx("submit_btn")} onClick={() => history.goBack()}>취소</CustomCancelButton>
                                </div>
                            </Fragment>
                        ) : <Loading/>}
                    </div>
                </div>
            </div>
            <CustomAlertModal open={isOpen} onClose={() => setIsOpen(!isOpen)} text="설정이 변경되었습니다" callBack={ () => history.goBack()}/>
       </Fragment>
    );
};

export default withRouter(PatchCallManagementDetail);