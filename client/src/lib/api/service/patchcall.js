import client from '../client';
import qs from "qs";

//050 번호
export const getAgent = (vno) => {
    return client.get(`/service/patchcall/management/detail?vno=${vno}`)
}

//050 번호 리스트
export const getAgentList = ({page,cate, search_filed, search}) => {
    const queryString = qs.stringify({
        page, cate,search_filed, search
    });

    return client.get(`/service/patchcall/management?${queryString}`)
}

//050번호 설정 변경
export const updateAgent = ({agent, address}) => {

    var addressForm = new FormData();



    $.each(address, function(key, value){
        addressForm.append(key, value);
    })


    const form = new FormData();
    form.append("vno",agent.vno)
    form.append("user_id",agent.userId)
    form.append("rcv_no",agent.rcvNo)
    form.append("name",agent.name)
    form.append("dong_yn",agent.dongYn)
    form.append("dong_name",agent.dongName)
    form.append("sms",agent.sms)
    form.append("out_sms",agent.out_sms)
    form.append("sms_yn",agent.smsYn)
    form.append("out_sms_yn",agent.outSmsYn)
    form.append("callback_no",agent.callback_no)
    form.append("out_sms_phone",agent.out_sms_phone)
    form.append("mns_file",agent.mmsFile)
    form.append("agent_address_info",JSON.stringify(address))

    if(agent.files !== null && agent.files !== undefined && agent.files.length !== 0 ){
        form.append("files",agent.files[0])
    }

    const config = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    return client.post(`/service/patchcall/management/edit`, form, config)
}

//콜로그 조회
export const getCalllogList = ({page,cate, search_filed, search,startDate, endDate}) => {
    const queryString = qs.stringify({
        page, cate,search_filed, search,startDate, endDate
    });

    return client.get(`/service/patchcall/call_log?${queryString}`)
}

//cid 조회
export const getCidList = ({page}) => {
    const queryString = qs.stringify({
        page
    });

    return client.get(`/service/patchcall/cid?${queryString}`)
}

//cid 리스트 전체조회
export const getAllCidList = () => {

    return client.get(`/service/patchcall/All_cid`)
}

//콜로그 다운로드
export const getCallLogListDownload = ({search_filed, search,startDate, endDate}) => {
    const queryString = qs.stringify({
        search_filed, search,startDate, endDate
    });

    return client.get(`/service/patchcall/call_log/excel_download?${queryString}`,{ responseType: 'arraybuffer' })
}

//콜로그 녹취 다운로드
export const getCallLogAudioDownload = (audioPath) => {
    return client.post(`/service/patchcall/call_log/audio_download`,audioPath,{ responseType: 'arraybuffer' })
}

//관리자용 번호 리스트
export const adminGetCalllogList = ({user_id,page,cate, search_filed, search,startDate, endDate}) => {
    const queryString = qs.stringify({
        user_id,page, cate,search_filed, search,startDate, endDate
    });

    return client.get(`/admin/call_log?${queryString}`)
}

//관리자용 콜로그 다운로드
export const adminGetCallLogListDownload = ({user_id,page,cate, search_filed, search,startDate, endDate}) => {
    const queryString = qs.stringify({
        user_id,page, cate,search_filed, search,startDate, endDate
    });

    return client.get(`/admin/call_log/excel_download?${queryString}`,{ responseType: 'arraybuffer' })
}

//관리자용 콜로그 녹취 다운로드
export const adminGetCallLogAudioDownload = (audioPath) => {
    return client.post(`/admin/call_log/audio_download`,audioPath,{ responseType: 'arraybuffer' })
}



//번호 관리 샘플 다운로드
export const callManageSampleDownload = () => {
    return client.get(`/service/patchcall/sample_download`,{ responseType: 'arraybuffer' })
}


//번호 관리 파일 업로드
export const numManageFileUpload = (excelFile) => {

    let formData = new FormData();
    formData.append("file", excelFile);

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return client.post(`/service/patchcall/excel_upload`,formData,config)
}