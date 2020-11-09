import React,{useState,useEffect} from 'react';
import Moment from "react-moment";

const AnalyticsManageItem = ({cx,item, onEdit, onDelete}) => {

    const [editData, setEditData] = useState({
        editing: false,
        idx:null,
        viewId : '',
        comments:''
    })

    // useEffect(() => {
    //     setEditData({
    //         viewId : item.viewId,
    //         comments: item.comments
    //     })
    // },[])

    const handleToggleEdit = () => {
        setEditData({
            idx: item.idx,
            viewId : item.viewId,
            comments: item.comments,
            editing: !editData.editing
        })
    }

    const onChangeValue = (e) => {
        const {name, value} = e.target
        setEditData({
            ...editData,
            [name]:value
        })

    }
    return (
        <tr className={cx("active_tr")}>
            <td>
                {item.rownum}
            </td>
            <td>
                {editData.editing ? (<input value={editData.viewId} name="viewId" onChange={(e) => {onChangeValue(e)}}/>) : (item.viewId)}
            </td>
            <td>
                {editData.editing ? (<input value={editData.comments} name="comments" onChange={(e) => {onChangeValue(e)}}/> ) : (item.comments)}
            </td>
            <td>
                <Moment format="YYYY-MM-DD">
                    {item.regDate}
                </Moment>
            </td>
            <td>
                {editData.editing ? (
                    <>
                        <button className={cx("btn")} onClick={() => {onEdit(editData)}}>저장</button>
                        <button className={cx("btn")} onClick={() => {onDelete(item.idx)}}>삭제</button>
                        <button className={cx("btn")} onClick={() => {handleToggleEdit()}}>취소</button>
                    </>

                    )
                :(
                    <button className={cx("btn")} onClick={() => {handleToggleEdit()}}>수정</button>
                    )}
            </td>
        </tr>
    );
};

export default AnalyticsManageItem;