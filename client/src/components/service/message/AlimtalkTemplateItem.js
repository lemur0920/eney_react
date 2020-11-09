import React from 'react';

const status = {
    'S': '중단',
    'A': '정상',
    'R': '대기',
};


const inspectionStatus = {
    'APR': '승인',
    'REG': '검수요청',
    'REQ': '승인대기',
    'REJ': '반려',
    'SRJ': '세종반려',
}



const AlimtalkTemplateItem = ({item, cx, deleteAction, history}) => {

    const moveDetail = (senderKey, templateCode) =>{
        history.push(`${location.pathname}/detail?senderkey=${senderKey}&templatecode=${templateCode}`)
    }
    return (
        <tr>
        {/*<tr>*/}
            <td>{item.name}</td>
            <td>{item.uuid}</td>
            <td>{item.template_name}</td>
            <td>{item.template_code}</td>
            <td>{ item.status != null ?  status[item.status] : '-' }</td>
            <td>{ item.inspectionStatus != null ?  inspectionStatus[item.inspectionStatus] : '-' }</td>
            <td>{item.createdAt}</td>
            <td>
                <button className={cx("btn")} onClick={() => {moveDetail(item.sender_key, item.template_code)} }>상세</button>
                <button className={cx("btn")} onClick={() => {deleteAction(item.sender_key, item.template_code)} }>삭제</button>
            </td>
        </tr>
    );
};

export default AlimtalkTemplateItem;