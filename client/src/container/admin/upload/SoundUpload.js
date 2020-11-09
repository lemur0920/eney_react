import React, {Fragment,useState,useEffect,useRef} from 'react';
import PageTitle from "../../../components/admin/pageTitle/PageTitle";
import Grid from "@material-ui/core/Grid";
import Widget from "../../../components/admin/widget/Widget";
import TextField from "@material-ui/core/TextField/TextField";
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select/Select";
import InputLabel from "@material-ui/core/InputLabel/InputLabel";
import {useDispatch, useSelector} from "react-redux";
import {soundUpload, initialize} from "../../../modules/admin/coloring/coloring";
import {withRouter} from 'react-router-dom';
import Button from "../../../components/common/Button";

const SoundUpload = ({cx,location,history}) => {
    const dispatch = useDispatch();

    const uploadForm = useRef();

    const {result,loading} = useSelector(({coloring,loading}) =>({
        result:coloring.uploadResult,
        loading: loading['coloring/SOUND_UPLOAD'],
    }))

    const [soundFile, setSoundFile] = useState({
        userid:"",
        type:"",
        ivr:"",
        description:"",
        file_name:"",
        files:null
    })

    const onChangeValue = (e) => {
        const {name, value} = e.target;

        if(name === 'files'){
            setSoundFile({
                ...soundFile,
                [name]: e.target.files
            })
        }else{
            setSoundFile({
                ...soundFile,
                [name]: value
            })
        }
    }

    useEffect(() => {
        if(!loading && result.result && result.error === null){
            uploadForm.reset();
            alert("업로드 완료")
        }
    },[dispatch,result,loading])

    const handleUploadFile = (e) => {
        const data = new FormData(e.target);
        dispatch(soundUpload(data))
    }
    return (
        <div>
            {/*{!loading && list !== null && (*/}
            <Fragment>
                <PageTitle title="파일 업로드" />
                <Grid container spacing={4}>
                    <Grid item xs={12}>
                        <Widget title="" upperTitle noBodyPadding disableWidgetMenu={true}>
                            <form className={cx("uploadForm")} onSubmit={(e) => {e.preventDefault(); handleUploadFile(e);}} ref={uploadForm} >
                                    <Fragment>
                                        <ul>
                                            <li>
                                                <label htmlFor="userid">사용자 아이디</label>
                                                <input
                                                    id="userid"
                                                    type="text"
                                                    value={soundFile.userid}
                                                    name="userid"
                                                    onChange={(e) => onChangeValue(e)}
                                                    required={true}
                                                />
                                            </li>
                                            <li>
                                                <label htmlFor="ivr">교환기</label>
                                                <select
                                                    id="ivr"
                                                    value={soundFile.ivr}
                                                    onChange={(e) => onChangeValue(e)}
                                                    name="ivr"
                                                    required={true}
                                                >
                                                    <option value="skb">SKB(0506)</option>
                                                    <option value="sejong">세종(0507)</option>
                                                </select>
                                            </li>
                                            <li>
                                                <label htmlFor="type">사용구분</label>
                                                <select
                                                    id="type"
                                                    value={soundFile.type}
                                                    onChange={(e) => onChangeValue(e)}
                                                    name="type"
                                                    required={true}
                                                >
                                                    <option value="coloring">컬러링</option>
                                                    <option value="rcvment">착신멘트</option>
                                                </select>
                                            </li>
                                            <li>
                                                <label htmlFor="file_name">파일이름</label>
                                                <input
                                                    id="file_name"
                                                    type="text"
                                                    value={soundFile.file_name}
                                                    name="file_name"
                                                    onChange={(e) => onChangeValue(e)}
                                                    required={true}
                                                />
                                            </li>
                                            <li>
                                                <label htmlFor="description">내용</label>
                                                <input
                                                    id="description"
                                                    type="text"
                                                    value={soundFile.description}
                                                    name="description"
                                                    onChange={(e) => onChangeValue(e)}
                                                    required={true}
                                                />
                                            </li>
                                            <li>
                                                <label htmlFor="files">파일첨부</label>
                                                <input
                                                    id="files"
                                                    type="file"
                                                    // value={soundFile.files}
                                                    name="files"
                                                    onChange={(e) => onChangeValue(e)}
                                                    accept = ".wav,.WAV"
                                                    required={true}
                                                />
                                            </li>
                                            <li>
                                                <Button eney>등록</Button>
                                            </li>
                                        </ul>
                                    </Fragment>

                                {/*)}*/}
                                {/*{showSave ? (*/}
                                {/*    <Fragment>*/}
                                {/*        <CustomButton eney  type="submit">저장</CustomButton>*/}
                                {/*        <CustomButton onClick={() => setShowSave(false)}>취소</CustomButton>*/}
                                {/*    </Fragment>*/}
                                {/*):(<CustomButton eney onClick={() => setShowSave(true)}>수정</CustomButton>)}*/}
                            </form>
                        </Widget>
                    </Grid>
                </Grid>

            </Fragment>


        </div>
    );
};

export default withRouter(SoundUpload);