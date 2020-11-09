import React, {Fragment, useState,useEffect,useRef} from 'react';
import Box from "@material-ui/core/Box";
import {TextField} from "@material-ui/core";

import useStyles from './style'
import cx from "classnames";
import UserCompanyNameChageModal from "./UserCompanyNameChageModal";
import UserCompanyNumChageModal from "./UserCompanyNumChageModal";
import UserCompanyKindChageModal from "./UserCompanyKindChageModal";
import {updateCompanyKind,uploadLicense,deleteLicense} from "../../../modules/user/mypage/mypage";
import fileupload, {lecenseUpload} from "../../../modules/util/fileupload";
import {useDispatch, useSelector} from "react-redux";
import CustomAlertModal from "../../common/CustomAlertModal";
import styled from "styled-components";
import Button from "../../common/Button";
import ThirdPartServiceApplyItem from "../../admin/third_part/ThirdPartServiceApplyItem";
import Dialog from "@material-ui/core/Dialog";
import IconButton from "@material-ui/core/IconButton";
import * as clientLib from "../../../lib/api/client";
import swal from 'sweetalert';


const CustomButton = styled(Button)`
    display: inline-block;
    margin:0;
    padding: 9px 16px;
`;
const UserCompanyInfo = ({name, number, type}) =>{

    const classes = useStyles();

    const inputRef = useRef();

    const dispatch = useDispatch();
    const {companyKindList,companyKind,loading,uploadLoading,licenseList,licenseUpload,deleteLicenseResult,deleteLicenseLoading} = useSelector(({mypage,loading,fileupload}) =>({
        companyKindList:mypage.companyKind,
        companyKind:mypage.user.company_kind,
        loading:loading['mypage/UPDATE_COMPANY_KIND'],
        licenseList:mypage.uploadLicense.list,
        deleteLicenseResult:mypage.deleteLicense,
        uploadLoading:loading['fileupload/LICENSE_UPLOAD'],
        uploadLicenseLoading:loading['mypage/UPLOAD_LICENSE'],
        deleteLicenseLoading:loading['mypage/DELETE_LICENSE'],
        licenseUpload:fileupload.license
    }))

    const [changeNameIsOpen, setChangeNameIsOpen] = useState(false);
    const [changeNumIsOpen, setChangeNumIsOpen] = useState(false);
    const [changeKindIsOpen, setChangeKindIsOpen] = useState(false);
    const [isPreview, setIsPreview] = useState(false);
    const [previewImage, setPreviewImage] = useState(null);

    const [licenseUploadFile, setLicenseUploadFile] = useState("");


    useEffect( () =>{
        dispatch(uploadLicense());
    },[])

    useEffect( () =>{

        if(!uploadLoading && licenseUpload.result && licenseUpload.error == null){
            inputRef.current.value = "";
            swal("사업자등록증 업로드가 완료되었습니다");
        }

        if(!uploadLoading && !licenseUpload.result && licenseUpload.error !== null){
            swal(licenseUpload.error);
        }

    },[licenseUpload,uploadLoading])

    useEffect( () =>{

        if(loading !== true){
            return
        }
        setChangeKindIsOpen(false);
        swal("업종이 변경되었습니다");

    },[companyKind])

    useEffect( () =>{

        if(!deleteLicenseLoading && deleteLicenseResult.result && deleteLicenseResult.error === null){
            swal("삭제가 완료되었습니다");
            dispatch(uploadLicense());
        }

    },[deleteLicenseLoading,deleteLicenseResult])

    const toggleNameModal = () =>{
        setChangeNameIsOpen(!changeNameIsOpen)
    }

    const toggleNumModal = () =>{
        setChangeNumIsOpen(!changeNumIsOpen)
    }

    const toggleKindModal = () =>{
        setChangeKindIsOpen(!changeKindIsOpen)
    }

    const onSubmit = (companyKind) =>{
        dispatch(updateCompanyKind(companyKind))
    }

    const handleLicenseUpload = () =>{
        dispatch(lecenseUpload(licenseUploadFile));
    }

    const handleLicenseDelete= (idx) =>{

        swal({
            text: "삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(deleteLicense(idx));
            }
        });

    }

    const handlePreview = (fileName) =>{
        setIsPreview(!isPreview);
        setPreviewImage(fileName)
    }




    return (
        <Fragment>
            <Box>
                <label className={cx(classes.label)}>기업명</label>
                <TextField className={cx(classes.fullInput)} fullWidth  variant="outlined" inputProps={{style: {fontSize: 14}}} value={name}/>
                {/*<Button className={cx(classes.changeButton)} onClick={toggleNameModal}>사업자명 변경</Button>*/}
            </Box>
            <Box>
                <label className={cx(classes.label)}>사업자등록번호</label>
                <TextField className={cx(classes.fullInput)} fullWidth  variant="outlined" inputProps={{style: {fontSize: 14}}} value={number}/>
                {/*<Button className={cx(classes.changeButton)} onClick={toggleNumModal}>등록번호 변경</Button>*/}
            </Box>
            <Box>
                <label className={cx(classes.label)}>업태/업종</label>
                <TextField className={cx(classes.divisionInput)} value={companyKind} variant="outlined" inputProps={{style: {fontSize: 14}}}/>
                <CustomButton eney className={cx(classes.changeButton)} onClick={toggleKindModal}>업태/업종 변경</CustomButton>
            </Box>
            <Box>
                <label className={cx(classes.label)}>사업자 등록증</label>
                <div className={cx(classes.fileInputBox)}>
                    <input type="file" className={cx(classes.fileInput)} ref={inputRef} onChange={(e) => setLicenseUploadFile(e.target.files[0])} />
                </div>
                <CustomButton eney className={cx(classes.businessLicenseAddBtn)} style={{marginTop:5}} onClick={() => handleLicenseUpload()}>사업자등록증 추가</CustomButton>
                {licenseList !== null && (
                    licenseList.map((item) => (
                        <div key={item.file_id} className={cx(classes.fileInputBox,classes.licenseBox)}>
                            <p className={cx(classes.fileInput)}>{item.name}</p>
                            <CustomButton eney  style={{marginTop:5}} value={item.upload_name+"."+item.extenstion} onClick={(e) => {handlePreview(e.target.value)}}>확인</CustomButton>
                            <CustomButton eney  style={{marginTop:5}} value={item.file_id} onClick={(e) => handleLicenseDelete(e.target.value)}>삭제</CustomButton>
                        </div>
                    ))
                )}
            </Box>
            <UserCompanyNameChageModal open={changeNameIsOpen} onClose={toggleNameModal}/>
            <UserCompanyNumChageModal open={changeNumIsOpen} onClose={toggleNumModal}/>
            <UserCompanyKindChageModal open={changeKindIsOpen} onClose={toggleKindModal} companyKindList={companyKindList} onSubmit={onSubmit}/>

            {isPreview && (
                <Dialog fullScreen open={isPreview} onClose={() => setIsPreview(false)}>
                    <IconButton  className={cx("modal_close_btn")} color="inherit"  onClick={() => setIsPreview(false)} aria-label="close">
                        {/*<CloseIcon />*/}
                        닫기
                    </IconButton>
                    <img
                        className={cx("image")}
                        src={`${clientLib.getUrl()}/file/LICENSE/${previewImage}`}
                        alt="no image"
                    />
                </Dialog>
            )}

        </Fragment>
    );
};

export default React.memo(UserCompanyInfo);