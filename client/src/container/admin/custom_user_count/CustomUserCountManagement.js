import React, {Fragment,useState,useEffect} from 'react';
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import {getCustomUserCount,updateCustomUserCount,changeField,initialize} from '../../../modules/admin/custom_user_count/custom_user_count'
import TextField from "@material-ui/core/TextField";
import {useDispatch, useSelector} from "react-redux";
import {makeStyles} from "@material-ui/core";
import styled from "styled-components";
import Button from "../../../components/common/Button";
import swal from 'sweetalert';



const CustomButton = styled(Button)`
    display: inline-block;
    margin: 30px 0 30px 30px;
    padding: 6px 16px;
    font-size: 0.875rem;
    margin: 8px;
    font-family: "noto";
    line-height: 1.75;
    
`;

const useStyles = makeStyles(theme => ({
    input: {
        margin:"10px 0 20px 30px"
    },
}));

const CustomUserCountManagement = () => {
    const classes = useStyles();

    const dispatch = useDispatch();

    const [showSave, setShowSave] = useState(false);

    const {count,error,result,loading} = useSelector(({custom_user_count,loading}) =>({
        count: custom_user_count.count,
        error: custom_user_count.error,
        result: custom_user_count.updateResult,
        loading: loading['adminCustomUserCount/GET_CUSTOM_USER_COUNT'],
    }))

    useEffect(() => {
        dispatch(getCustomUserCount());
    },[])

    useEffect(() => {
        if(error === null && result){
            dispatch(initialize("updateResult"))
            setShowSave(false)
            swal("수정이 완료되었습니다");
        }
    },[result])

    const changeValue = ({name, value}) => {
        dispatch(changeField({
            key:name,
            value
        }))

    }

    const onCountSave = () => {
        swal({
            text: "수정하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                dispatch(updateCustomUserCount(count));
            }
        });
    }

    return (
        <div>
            {/*{!loading && list !== null && (*/}
            <Fragment>
                <PageTitle title="카운트 관리" />
                <Grid container spacing={4}>
                    <Grid item xs={12}>
                        <Widget title="" upperTitle noBodyPadding>
                            <form onSubmit={(e) => {e.preventDefault(); onCountSave()}}>
                                {!loading && (
                                    <Fragment>
                                    <TextField
                                        label="웹 로그 카운트"
                                        type="number"
                                        value={count.web_log}
                                        name="web_log"
                                        onChange={(e) => changeValue(e.target)}
                                        className={classes.input}
                                        disabled={!showSave}
                                        InputProps={{
                                            readOnly: !showSave,
                                        }}
                                        InputLabelProps={{
                                            shrink: true,
                                            // inputprops: { min: 1}
                                        }}
                                        required={true}
                                    />
                                    <TextField
                                    label="법인 고객 카운트"
                                    type="number"
                                    value={count.corp}
                                    name="corp"
                                    onChange={(e) => changeValue(e.target)}
                                    className={classes.input}
                                    disabled={!showSave}
                                    InputProps={{
                                    readOnly: !showSave,
                                }}
                                    InputLabelProps={{
                                    shrink: true,
                                    inputprops: { min: 1}
                                }}
                                    required={true}
                                    />
                                {/*    <TextField*/}
                                {/*    label="개인 고객 카운트"*/}
                                {/*    type="number"*/}
                                {/*    name="personal"*/}
                                {/*    value={count.personal}*/}
                                {/*    onChange={(e) => changeValue(e.target)}*/}
                                {/*    className={classes.input}*/}
                                {/*    disabled={!showSave}*/}
                                {/*    InputProps={{*/}
                                {/*    readOnly: !showSave,*/}
                                {/*}}*/}
                                {/*    InputLabelProps={{*/}
                                {/*    shrink: true,*/}
                                {/*    inputprops: { min: 1}*/}
                                {/*}}*/}
                                {/*    required={true}*/}
                                {/*    />*/}
                                        <TextField
                                            label="방문자"
                                            type="number"
                                            name="visit"
                                            value={count.visit}
                                            onChange={(e) => changeValue(e.target)}
                                            className={classes.input}
                                            disabled={!showSave}
                                            InputProps={{
                                                readOnly: !showSave,
                                            }}
                                            InputLabelProps={{
                                                shrink: true,
                                                inputprops: { min: 1}
                                            }}
                                            required={true}
                                        />
                                        <TextField
                                            label="api"
                                            type="number"
                                            name="api"
                                            value={count.api}
                                            onChange={(e) => changeValue(e.target)}
                                            className={classes.input}
                                            disabled={!showSave}
                                            InputProps={{
                                                readOnly: !showSave,
                                            }}
                                            InputLabelProps={{
                                                shrink: true,
                                                inputprops: { min: 1}
                                            }}
                                            required={true}
                                        />
                                    </Fragment>

                                )}
                                {showSave ? (
                                    <Fragment>
                                        <CustomButton eney  type="submit">저장</CustomButton>
                                        <CustomButton onClick={() => setShowSave(false)}>취소</CustomButton>
                                    </Fragment>
                                ):(<CustomButton eney onClick={() => setShowSave(true)}>수정</CustomButton>)}
                            </form>
                        </Widget>
                    </Grid>
                </Grid>

            </Fragment>


        </div>
    );
};

export default CustomUserCountManagement;