import React, {Fragment,useState,useEffect} from 'react';
import {withRouter} from 'react-router-dom';
import CustomModal from "../../../components/common/CustomModal";
import {numManamgeFileUpload,initializeForm,initialize} from "../../../modules/service/patchcall";
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import swal from 'sweetalert';
import Loading from "../../../components/util/Loading";

const PatchCallManagementBulkFormModal = ({open, onClose,cx,sampleDownload,result,error,location,history}) => {
    const dispatch = useDispatch();

    const [uploadFile, setUploadFile] = useState(null);

    const title = "번호 수정 일괄 처리";
    const subTitle = "";

    const {bulkUpload,loading} = useSelector(({patchcall,loading}) =>({
            bulkUpload:patchcall.bulkUpload,
            loading:loading['patchcall/NUM_MANAGE_FILE_UPLOAD'],
        }))

    useEffect(() => {
        if(bulkUpload.result && bulkUpload.error === null){
            dispatch(initializeForm('bulkUpload'));
            const {page=1,search_filed = null, search = null,startDate = null, endDate = null} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });

            history.push(`${location.pathname}?page=${page}${`${search_filed !== null ? (`'&search_filed='${search_filed}`) : ""}`}${`${search !== null ? (`'&search='${search}`) : ""}`}${`${startDate !== null ? (`&startDate=${moment(startDate).format('YYYYMMDD')}`) : ""}`}${`${endDate !== null ? (`&endDate=${moment(endDate).format('YYYYMMDD')}`) : ""}`}`)

            swal("번호 수정 완료").then(() => {
                onClose();
            })
        }

    },[bulkUpload])

    const handleSubmit = (e) => {
        dispatch(numManamgeFileUpload(uploadFile))
    }



    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            {/*<Box>*/}
                <p>양식에 맞게 입력 후 파일을 업로드해주세요.</p>
                <p>착신전환 번호, TTS엔진 이용을 위한 가맹점 변경만 가능합니다.</p>
                <div className={cx("call_manage_bulk_modal","tb_style_1","wrap_btn")}>

                    {loading ? (<Loading/>) : (

                        <form onSubmit={(e) =>{e.preventDefault(); handleSubmit();}} name='excelUploadForm'>
                            <div>
                                <input className={cx("excel_upload_input")} id="bulkUpload"  type="file" onChange={(e) => {setUploadFile(e.target.files[0]);}} required={true}/>
                            </div>
                            {bulkUpload.error !== null && (
                                <p className={cx("error_msg")}>{bulkUpload.error}</p>
                            )}
                            <button type="button" className={cx("btn_g","btn_g2","sample_download")} onClick={() => sampleDownload()}>양식 다운로드</button>
                            <button type="submit" className={cx("btn_g","btn_g2","closeBtn")}>수정</button>
                            <button type="button" className={cx("btn_g","closeBtn")} onClick={(e)=>{onClose();}}>닫기</button>
                        </form>
                        )}
                </div>
            {/*</Box>*/}
        </CustomModal>
    );
};

export default withRouter(PatchCallManagementBulkFormModal);