import client from '../client';
import qs from "qs";

//상단 카운트, 전화율
export const getCompareData = () => {
    return client.get(`/service/dashboard/compare_data`)
}

//Call Ratio
export const getCallRatio = () => {
    return client.get(`/service/dashboard/call_ratio`)
}

//날짜 기준 콜 데이터
export const getDateByCallData = ({startDate,endDate}) => {
    return client.get(`/service/dashboard/getDateByCallCount/${startDate}/${endDate}`)
}

//시간 기준 콜 데이터
export const getTimeByCallCount = (date) => {
    return client.get(`/service/dashboard/getTimeByCallCount/${date}`)
}

//시간 기준 평균 콜 데이터 + 하단 그래프 데이터들
export const getTimeAvgCallCount = ({startDate,endDate}) => {
    return client.get(`/service/dashboard/getTimeAvgCallCount/${startDate}/${endDate}`)
}


//050번호 설정 변경
export const updateAgent = (agent) => {

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
//
//
// export const addCustomerCase = (form) => {
//     const config = {
//         headers: {
//             'Content-Type': 'application/x-www-form-urlencoded'
//         }
//     };
//
//     return client.post(`/customer_case`,form,config)
// }
//
// export const editCustomerCase = (form) => {
//     console.log("수정")
//
//     const config = {
//         headers: {
//             'Content-Type': 'application/x-www-form-urlencoded'
//         }
//     };
//
//     return client.post(`/customer_case/edit`,form,config)
// }
//
// export const deleteCustomerCase = (idx) => {
//     return client.delete(`/customer_case/${idx}`)
// }
// // export const updateCustomUserCount = (data) => {
// //     return client.put(`/admin/customUserCount`,data)
// // }