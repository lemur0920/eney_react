import React, {useEffect,useState} from 'react';
import {withRouter} from 'react-router-dom'
import {useDispatch, useSelector} from "react-redux";
import qs from "qs";
import BoardEditor from "../../../components/customerservice/common/BoardEditor";
import {changeField,boardWrite,boardUpdate,changeFile,reset,board,changeGroup, changeCondition,setBoardInfo} from "../../../modules/customerservice/customerservice";
import {imageUpload,resetFile} from "../../../modules/util/fileupload";
import properties from "../../../properties";

const BoardEdit = ({type,location, history,match}) => {

    const dispatch = useDispatch();
    const [error, setError] = React.useState(null);
    const [call, setCall] = useState(null);


    const {boardItem, boardInfo, loading,checkError,result,imageUrl,uploadLoading} = useSelector(({customerservice,loading,fileupload}) =>({
        boardItem: customerservice.board,
        boardInfo: customerservice.boardInfo,
        loading:loading['customerservice/BOARD'],
        checkError:customerservice.checkError,
        result:customerservice.result,
        imageUrl:fileupload.imageUrl,
        uploadLoading:loading['fileupload/IMAGE_UPLOAD'],
    }))


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

    useEffect( () => {

        if(type === "edit"){
            const {type,id} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });
            // return () => {
                dispatch(reset());
            // }
            dispatch(board({type,id}))
        }else{
            const {type,id} = qs.parse(location.search,{
                ignoreQueryPrefix:true,
            });
            // dispatch(reset())
            dispatch(setBoardInfo({type}))
        }

        return () => {
            dispatch(resetFile());
        }

    },[])

    // useEffect( () => {
    //     if(imageUrl){
    //         call(properties.WEB_SERVER_IMAGE+imageUrl)
    //     }
    // },[imageUrl])


    const onSubmit = (content) => {
        // e.preventDefault();

        console.log("글 저장")

        // if(!boardItem.title || !boardItem.content){
        //     setError("제목 및 내용을 입력하세요.")
        //     return
        // }

        const {type} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        console.log(boardItem)
        console.log(type)

        dispatch(boardWrite({boardItem, content,type}))

    }

    const onUpdate = (content) => {
        // e.preventDefault();

        console.log("수정")

        if(!boardItem.title || !boardItem.content){
            setError("제목 및 내용을 입력하세요.")
            return
        }

        const {type} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        // dispatch(
        //     changeField({
        //         key:"content",
        //         value:content
        //     })
        // )

        console.log("####업데이트######")
        console.log(content)
        console.log("####업데이트######")
        dispatch(boardUpdate({boardItem,content, type}))

    }

    const insertImage =({image}) => {
        console.log(image)
        // console.log(callback)
        // setCall(() => callback)
        dispatch(imageUpload({image}))
    }



    const onChange = e =>{

        const {value, name} = e.target;


        console.log(value, name)
        dispatch(
            changeField({
                key:name,
                value
            })
        )
    }

    const onChangeFile = (files) => {
        dispatch(changeFile(files))
    }

    const onChangeGroup = (e) => {
        const value = parseInt(e.target.value)
        dispatch(changeGroup(value))
    }

    const onChangeCondition = (e) => {
        const value = parseInt(e.target.value)
        dispatch(changeCondition(value))
    }

    return (
        <div>
            {!loading &&(
                <BoardEditor board={boardItem} type={type} onChangeGroup={onChangeGroup} onChangeCondition={onChangeCondition} onChange={onChange} onSubmit={type === "write" ? onSubmit : onUpdate} boardInfo={boardInfo} insertImage={insertImage} imageUrl={imageUrl} onChangeFile={onChangeFile} error={error}/>
            )}
        </div>
    );
};

export default withRouter(BoardEdit);