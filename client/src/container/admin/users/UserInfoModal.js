import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {getUserInfo,updateUserInfo,initializeForm} from "../../../modules/admin/users";
import CustomModal from "../../../components/common/CustomModal";
import Loading from "../../../components/util/Loading";
import Dialog from "@material-ui/core/Dialog";
import IconButton from "@material-ui/core/IconButton";
import * as clientLib from "../../../lib/api/client";
import Button from "../../../components/common/Button";
import swal from 'sweetalert';

const UserInfoModal = ({open, onClose, cx,userIdx}) => {
    const title = "";
    const subTitle = "";

    const [isPreview, setIsPreview] = useState(false);
    const [previewImage, setPreviewImage] = useState(null);
    const [authList, setAuthList] = useState({
        idx:userIdx,
        auth_patch_call:false,
        auth_patch_dash:false,
        auth_patch_ai:false,
        auth_patch_cloud:false,
    });

    const dispatch = useDispatch();

    const {userInfo,userInfoUpdate,loading,updateLoading} = useSelector(({users,loading}) =>({
        userInfo:users.userInfo,
        userInfoUpdate:users.userInfoUpdate,
        loading: loading['users/GET_USER_INFO'],
        updateLoading: loading['users/UPDATE_USER_INFO']
    }))

    useEffect(() => {
        return () => {
            dispatch(initializeForm("userInfoUpdate"))
        }

    },[])

    useEffect(() => {
        const data = {
            idx:userIdx
        }
        dispatch(getUserInfo(data))

    },[userIdx])

    useEffect(() => {
        if(!loading && userInfo.result && userInfo.error === null){
            setAuthList({
                ...authList,
                auth_patch_call:userInfo.info.auth_patch_call,
                auth_patch_dash:userInfo.info.auth_patch_dash,
                auth_patch_ai:userInfo.info.auth_patch_ai,
                auth_patch_cloud:userInfo.info.auth_patch_cloud,
            })
        }

    },[userInfo,loading])

    useEffect(() => {
        if(!updateLoading && userInfoUpdate.result && userInfoUpdate.error === null){
            swal("수정 완료");
            onClose();
        }

    },[updateLoading,userInfoUpdate])



    const handlePreview = (fileName) =>{
        setIsPreview(!isPreview);
        setPreviewImage(fileName)
    }

    const onChangeInfo = (e) => {
        const {name, checked} = e.target;

        setAuthList({
            ...authList,
            [name]:checked
        })
    }


    const onInfoSave = () => {
        dispatch(updateUserInfo(authList,userIdx));

    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <div className={cx("popup","user_info_popup")}>
                <div className={cx("popup-content")}>
                    {/*<h1>그룹 멤버 관리 ({group_name})</h1>*/}
                    <div className={cx("inner")}>
                        <div className={cx("tb_style_2")}>
                            <table>
                                <colgroup>
                                    <col style={{width:"23.5%"}}/>
                                    <col style={{width:"19.29%"}}/>
                                    <col/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th scope="col">구분</th>
                                    <th scope="col"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>사업자등록증</td>
                                    <td>
                                        {userInfo.license !== null && (
                                            userInfo.license.map((item) => (
                                                <div key={item.file_id}>
                                                    <button value={item.upload_name+"."+item.extenstion} onClick={(e) => {handlePreview(e.target.value)}}>{item.name}</button>
                                                </div>
                                            ))
                                        )}
                                    </td>
                                </tr>
                                <tr>
                                    <td>서비스 페이지 권한</td>
                                    <td>
                                        <div>
                                            <label htmlFor="patch">패치콜</label><input id="patch" type="checkbox" name="auth_patch_call" onChange={(e) => onChangeInfo(e)} checked={authList.auth_patch_call}/>
                                            <label htmlFor="patch_dash">패치콜 대시보드</label><input id="patch_dash" name="auth_patch_dash" type="checkbox" onChange={(e) => onChangeInfo(e)} checked={authList.auth_patch_dash}/>
                                            <label htmlFor="patch_ai">패치콜 AI</label><input id="patch_ai" name="auth_patch_ai" type="checkbox" onChange={(e) => onChangeInfo(e)} checked={authList.auth_patch_ai}/>
                                            <label htmlFor="patch_cloud">패치콜 Cloud</label><input id="patch_cloud" name="auth_patch_cloud" type="checkbox" onChange={(e) => onChangeInfo(e)} checked={authList.auth_patch_cloud}/>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div className={cx("btn_box")}>
                            <Button eney onClick={() => onInfoSave()}>저장</Button>
                        </div>
                        <div className={cx("popup_close")}>
                            <button onClick={() => onClose()}><span></span></button>
                        </div>
                    </div>
                </div>
            </div>
            {isPreview && (
                <Dialog fullScreen open={isPreview} onClose={() => setIsPreview(false)}>
                    <IconButton  className={cx("modal_close_btn")} color="inherit"  onClick={() => setIsPreview(false)} aria-label="close">
                        닫기
                    </IconButton>
                    <img
                        className={cx("image")}
                        src={`${clientLib.getUrl()}/file/LICENSE/${previewImage}`}
                        alt="no image"
                    />
                </Dialog>
            )}
        </CustomModal>
    );
};

export default UserInfoModal;