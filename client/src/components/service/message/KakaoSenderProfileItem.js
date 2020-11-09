import React from 'react';

const KakaoSenderProfileItem = ({item, cx, deleteAction}) => {
    console.log(item);
    return (
        <tr>
            <td>{item.userid}</td>
            <td>{item.name}</td>
            <td>{item.uuid}</td>
            <td>{item.sender_key}</td>
            <td>{item.created_at}</td>
            <td>
                <button className={cx("btn")} onClick={() => {deleteAction(item.sender_key)} }>삭제</button>
            </td>
        </tr>
    );
};

export default KakaoSenderProfileItem;