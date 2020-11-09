import React, {Fragment, useState} from 'react';
import Moment from "react-moment";
import Button from "../../common/Button";
import Dialog from "@material-ui/core/Dialog";
import IconButton from "@material-ui/core/IconButton";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const ColoringItem = ({cx,item}) => {
    const [isOpen, setIsOpen] = useState(false);

    const openScript = () => {
        setIsOpen(!isOpen)
    }

    return (
        <Fragment>
            <TableRow className={cx("customer_case_tr")}>
                <TableCell align="center">{item.rownum}</TableCell>
                <TableCell align="center">
                    {item.userid}
                </TableCell>
                <TableCell align="center">
                    <Button type="button" onClick={() => openScript()}>확인</Button>
                </TableCell>
                <TableCell align="center">
                    {item.voice_tone}
                </TableCell>
                <TableCell align="center">{item.bgm}</TableCell>
                <TableCell align="center">
                    <Moment format="YYYY-MM-DD">{item.write_date}</Moment>
                </TableCell>
                <TableCell align="center">
                    {item.status}
                </TableCell>
                <TableCell align="center">
                    <Button type="button" onClick={() => openScript()}>관리</Button>
                </TableCell>
            </TableRow>
            {isOpen && (
                <Dialog fullScreen open={isOpen} onClose={() => openScript()}>
                    <IconButton  className={cx("modal_close_btn")} color="inherit"  onClick={() => openScript()} aria-label="close">
                        닫기
                    </IconButton>
                    <p>
                        {item.script}
                    </p>
                </Dialog>
            )}
        </Fragment>
    );
};

export default ColoringItem;