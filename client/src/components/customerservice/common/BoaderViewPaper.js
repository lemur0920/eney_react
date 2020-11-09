import React, {Fragment, useEffect} from 'react';
import {withRouter,Link} from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
// import Button from "@material-ui/core/Button";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Moment from "react-moment";
import Box from "@material-ui/core/Box";
import p from'../../../properties';
// import Quill from 'quill';
import Button from "../../../components/common/Button";
import styled from "styled-components";
const CustomButton = styled(Button)`
  //padding: 4px 5px;
  //margin:2px;
  float:right;
  //margin:10px 0 0 0;
`;

const useStyles = makeStyles(theme => ({
    root: {
        padding: theme.spacing(3, 2),
    },
    titleCell:{
        fontSize:"1.2rem",
        width:"55%",
        paddingLeft: 40,
        // borderBottom:"1px solid gray"
    },
    topCell:{
        color:"gray",
        fontWeight:300,
        // borderBottom:"1px solid gray"
    },
    inner:{
        padding:50,
        ['@media (max-width:768px)']: {
            padding:10,
        },
    },
    button: {
        color:"white",
        margin: theme.spacing(1),
        float:"right"
    },
    noticeBadge:{
        left: 0,
        top: "14px",
        width: "48px",
        height: "22px",
        lineHeight: "22px",
        backgroundColor: "#505558",
        borderRadius: "10px",
        textAlign: "center",
        fontSize: "14px",
        color: "#fff",
        margin: "0 auto"
    }
}));

const BoaderViewPaper = ({board,onDelete,moveUpdatePage ,boardInfo, user, history}) => {
    const classes = useStyles();

    let quill;
    let Quill;
    let quillBetterTable;

    const isBrowser = process.env.APP_ENV === 'browser';

    useEffect( () => {
        // let Viewer;
        // const isBrowser = process.env.APP_ENV === 'browser';
        if(isBrowser) {

            Quill = require('quill');
            quillBetterTable = require('quill-better-table');

            quill = new Quill('#viewerSection', {
                readOnly:true
            });

            quill.clipboard.dangerouslyPasteHTML(0, board.content);
        //
        //     let Viewer = require('tui-editor/dist/tui-editor-Viewer')
        // console.log("게시글 보기")
        //     const instance = new Viewer({
        //         el: document.querySelector('#viewerSection'),
        //         height: '500px',
        //         initialValue: board.content
        //     });

        }

    },[])

    return (
        <Fragment>
        <Box borderRadius={7}>
        <Table>
            <TableHead>
                <TableRow>
                    <TableCell className={classes.titleCell}>
                        {board.title}
                    </TableCell>
                    <TableCell className={classes.topCell}>
                        {boardInfo.group && (
                            boardInfo.group[board.group_number]
                        )}
                    </TableCell>
                    <TableCell className={classes.topCell}>
                        {boardInfo.condition && (
                            <div className={classes.noticeBadge}>{boardInfo.condition[board.condition_number]}</div>
                        )}
                    </TableCell>
                    {/*<TableCell className={classes.topCell}>*/}
                    {/*    <Moment format="YYYY-MM-DD">*/}
                    {/*        {board.write_date}*/}
                    {/*    </Moment>*/}
                    {/*</TableCell>*/}
                    <TableCell className={classes.topCell}>
                        {board.hits} hit
                    </TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                <TableRow>
                    <TableCell colSpan={5}>
                        <div className={classes.inner} id="viewerSection"></div>
                    </TableCell>
                </TableRow>
            </TableBody>
        </Table>
            {board.files && (
                <a href={p.WEB_SERVER_FILE+board.files.upload_name+"."+board.files.extenstion+"/"+board.files.name} target="_blank" >{board.files.name}</a>
            )}
        </Box>
            {(user && user.role === "ADMIN") && (
                <Fragment>
                    <CustomButton
                        eney
                        // variant="contained"
                        // color="primary"
                        // className={classes.button}
                        onClick={moveUpdatePage}
                    >
                        수정
                    </CustomButton>
                    <CustomButton
                        eney
                        // variant="contained"
                        // color="primary"
                        // className={classes.button}
                        onClick={onDelete}
                    >
                        삭제
                    </CustomButton>
                </Fragment>
                )}
        </Fragment>

    );
};

export default withRouter(BoaderViewPaper);