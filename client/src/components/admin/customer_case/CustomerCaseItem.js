import React,{Fragment,useState} from 'react';
import {Link,withRouter} from "react-router-dom";
import Moment from "react-moment";
import Button from "../../common/Button";
import Dialog from '@material-ui/core/Dialog';
import client from '../../../lib/api/client';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import * as clientLib from '../../../lib/api/client';

const CustomerCaseItem = ({item,moveEdit,handleDelete,cx,location,history, match}) => {
    const [isOpen, setIsOpen] = useState(false);

    const openTumbnail = () => {
        setIsOpen(!isOpen)
    }

    return (
        <Fragment>
            <tr className={cx("customer_case_tr")}>
                <td align="center">{item.rownum}</td>
                <td align="center">
                    <a href={item.link} target="_blank">링크 확인</a>
                </td>
                <td align="center">
                    {item.clients}
                </td>
                <td align="center">{item.project}</td>
                <td align="center">
                    <Moment format="YYYY-MM-DD">{item.release_date}</Moment>
                </td>
                <td align="center">
                    {item.code_name}
                </td>
                <td align="center">
                    <Button eney onClick={() => {openTumbnail()}}>확인</Button>
                </td>

                <td align="center">
                    <Button eney onClick={() => {moveEdit(item.idx)}}>수정</Button>
                    <Button eney onClick={() => {handleDelete(item.idx)}}>삭제</Button>
                </td>
            </tr>
            {isOpen && (
                <Dialog fullScreen open={isOpen} onClose={() => openTumbnail()}>
                    <IconButton  className={cx("modal_close_btn")} color="inherit"  onClick={() => openTumbnail()} aria-label="close">
                        {/*<CloseIcon />*/}
                        닫기
                    </IconButton>
                         <img
                             className={cx("image")}
                             src={`${clientLib.getUrl()}/customer_case/tumbnail/${item.tumbnail_file}`}
                             // onClick={openTumbnail()}
                             alt="no image"
                         />
                </Dialog>
            )}
        </Fragment>
    );
};

export default withRouter(CustomerCaseItem);