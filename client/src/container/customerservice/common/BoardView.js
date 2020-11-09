import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import {withRouter} from 'react-router-dom'
import {board, boardDelete, reset} from "../../../modules/customerservice/customerservice";
import BoaderViewPaper from "../../../components/customerservice/common/BoaderViewPaper";
import {makeStyles} from "@material-ui/core";
import palette from "../../../lib/styles/palette";
import Button from "../../../components/common/Button";
import styled from "styled-components";
import swal from 'sweetalert';


const useStyles = makeStyles(theme => ({
    button: {
        color:"white",
        margin: 8,
        backgroundColor: palette.cyan[0],
        float:"right",
        fontSize: "0.875rem",
        padding:"6px 16px",
        boxShadow:"none",
        "&.hover:":{
            boxShadow:"none",
        }
    },
}));

const CustomButton = styled(Button)`
    float:right;
`;

const BoardView = ({location,history}) => {

    const dispatch = useDispatch();
    const classes = useStyles();

    const {boardItem,boardInfo,result,checkError, loading,user} = useSelector(({customerservice,loading,auth}) =>({
        boardItem: customerservice.board,
        boardInfo: customerservice.boardInfo,
        result: customerservice.result,
        loading:loading['customerservice/BOARD'],
        checkError:customerservice.checkError,
        user:auth.user

    }))

    const onDelete = () =>{
        swal({
            text: "삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(boardDelete({boardItem}))
            }
        });
    }

    const moveUpdatePage = () => {
        history.push(`${location.pathname}/edit${location.search}`)
    };

    const moveBack = () => {
        history.goBack();
    };

    // useEffect(() => {
    //     // const {type, id} = qs.parse(location.search,{
    //     //     ignoreQueryPrefix:true,
    //     // });
    //     //
    //     // if(loading || id === undefined){
    //     //     return;
    //     // }else{
    //     //     dispatch(board({type, id}))
    //     // }
    //     // return () => {
    //     //     dispatch(reset());
    //     // }
    // });



    useEffect(() => {

        const {type, id} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(loading || id === undefined){
            return;
        }else{
            dispatch(board({type, id}))
        }

        // return () => {
        //     dispatch(reset());
        // }


    },[location.search])

    useEffect( () => {
        if(result){
            const {type} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });
            history.push(`/customerservice?type=${type}`)
        }
        return () => {
            dispatch(reset());
        }
    },[dispatch,result])


    return (
        <div>
            {!loading && boardItem &&
            (
                <BoaderViewPaper board={boardItem} boardInfo={boardInfo} onDelete={onDelete} moveUpdatePage={moveUpdatePage} user={user}/>
            )
            }
            {/*<button variant="contained" className={classes.button} color="primary" onClick={moveBack}>뒤로가기</button>*/}
            <CustomButton eney onClick={moveBack}>뒤로가기</CustomButton>
        </div>
    );
};

export default withRouter(BoardView);