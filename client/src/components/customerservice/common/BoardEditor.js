import React, {useEffect, useState,Fragment,useRef} from 'react';
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core";
import Input from "@material-ui/core/Input";
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import TableIcon from '../../../asset/image/svg/icon.svg'


const useStyles = makeStyles(theme => ({
    topBox: {
        padding: theme.spacing(1, 1),
        borderTop: "1px solid lightgray",
        borderLeft: "1px solid lightgray",
        borderRight: "1px solid lightgray",
    },
    label:{
        padding:30,
        width:"20%"
    },
    inputLabel:{
        marginLeft:50,
        padding:10,
        width:"20%",
        fontSize:"1rem"
    },
    input:{
        width:"33%"
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    fileBox:{
        display: "inline-block",
        height: 30,
        lineHeight: 4,
        marginLeft: 40
    },
    button: {
        color:"white",
        margin: theme.spacing(1),
        float:"right"
    },
}));



const BoardEditor = ({board, type, onChange, onChangeGroup, onChangeCondition, boardInfo,onChangeFile,onSubmit,error,insertImage}) => {

    const classes = useStyles();

    let quill;
    let Quill = null;

    const [videoUrl, setVideoUrl] = useState("");


    const groupArray = boardInfo.group;
    const conditionArray = boardInfo.condition;

    const insertVideo = () => {

        const range = window.quill.getSelection(true);
        window.quill.insertEmbed(range.index, 'video', videoUrl);
        setVideoUrl("");
    }


    const writeContent = (value) => {
        const e = {
            target:{
                name:"content",
                value: value
            }
        };
        onChange(e)
    }

    const changeFile = (event) => {
        const files = event.target.files[0];
        const e = {
            target:{
                name:"files",
                value:files
            }
        };
        onChange(e)
    }

    const imageHandler = () =>
    {
        const input = document.createElement('input');

        input.setAttribute('type', 'file');
        input.setAttribute('accept', 'image/*');
        input.click();

        input.onchange = async () => {

            const file = input.files[0];

            // insertImage(file)

            const formData = new FormData();

            formData.append('files', file);

            let xhr = new XMLHttpRequest();
            xhr.open('POST', 'http://210.103.188.124:4005/file/imageUpload', true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    let response = xhr.response;
                    if (response !== '') {
                        // Save current cursor state
                        const range = window.quill.getSelection(true);

                        window.quill.setSelection(range.index + 1);
                        window.quill.deleteText(range.index, 1);
                        window.quill.insertEmbed(range.index, 'image', "http://210.103.188.124:4005/file/image/"+response);
                    } else {
                        alert("error")
                    }
                }
            }
            xhr.send(formData);

        };
    }

    const addTable = () => {
        let tableModule = window.quill.getModule('better-table')
        tableModule.insertTable(3, 3)
    }

    useEffect( () => {
        const isBrowser = process.env.APP_ENV === 'browser';
        if(isBrowser) {

            let w = window;
            let s = document.createElement('link');
            s.rel = 'stylesheet';
            s.async = true;
            s.href = 'https://unpkg.com/quill-better-table@1.2.8/dist/quill-better-table.css';
            s.charset = 'UTF-8';
            let x = document.getElementsByTagName('link')[0];
            x.parentNode.insertBefore(s, x);

            // document.head.innerHTML += '<link rel="stylesheet" type="text/css" href="styles.css" />';

            Quill = require('quill');

            if(Quill !== null){

                const quillBetterTable = require('quill-better-table');
                require('react-quill/dist/quill.snow.css');

                Quill.register({
                    'modules/better-table': quillBetterTable
                }, true)

                let tools = [
                    [{ 'font': [] }, { 'size': [] }],
                    [{ 'align': []}],
                    [ 'bold', 'italic', 'underline', 'strike' ],
                    [{ 'color': [] }, { 'background': [] }],
                    [{ 'script': 'super' }, { 'script': 'sub' }],
                    [{ 'header': '1' }, { 'header': '2' }, 'blockquote', 'code-block' ],
                    [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
                    [ 'link', 'image'],
                    [ 'clean' ]
                ]
                window.quill = new Quill('#editor', {
                    theme: 'snow',
                    modules: {
                        toolbar: tools,
                        table: false,  // disable table module
                        'better-table': {
                            operationMenu: {
                                items: {
                                    unmergeCells: {
                                        text: 'Another unmerge cells name'
                                    }
                                },
                                color: {
                                    colors: ['green','red', 'yellow', 'blue', 'white'],  // colors in operationMenu
                                    text: 'Background Colors'  // subtitle
                                }
                            }
                        },
                        keyboard: {
                            bindings: quillBetterTable.keyboardBindings
                        }
                    },
                    placeholder: 'Insert...',

                    image: imageHandler
                });

            }


            ///SET Editor add TableButton///
            let node = document.createElement("span");
            node.classList.add( 'ql-formats' );
            let button = document.createElement("button");
            button.setAttribute( 'type', 'button' )
            button.addEventListener("click", function() {
                addTable()
            });
            let img = document.createElement("img");

            img.src = TableIcon
            button.append(img);
            node.append(button)
            document.body.querySelector('.ql-toolbar').appendChild(node);

            window.quill.getModule('toolbar').addHandler('image',function(){
                imageHandler()
            })
            ///SET Editor add TableButton///


            //에디터 기본 값
            window.quill.clipboard.dangerouslyPasteHTML(0, (board && type == "edit" ? board.content : ""));
        }

    },[])

    const set = () => {
    }
    return (
        <Fragment>
            <p style={{color:"red"}}>{error}</p>
            <form onSubmit={(e) => {e.preventDefault(); onSubmit(window.quill.container.firstChild.innerHTML)}}>
                <div className={classes.topBox}>
                    <label className={classes.label} htmlFor="titleInput">제목</label>
                    <Input color="secondary" className={classes.input} id="titleInput" type="text" name="title" onChange={onChange} value={board.title}/>
                    <label className={classes.inputLabel} htmlFor="fileInput">첨부파일</label>
                    <Input color="secondary" className={classes.input} id="fileInput" type="file" name="files" onChange={e => changeFile(e)}/>
                </div>
                <div className={classes.topBox}>
                    <label className={classes.label} htmlFor="urlInput">Video Url</label>
                    <Input color="secondary" className={classes.input} id="urlInput" type="text" name="url" onChange={(e) => setVideoUrl(e.target.value)} value={videoUrl}/>
                    <button type="button" onClick={() => insertVideo()}>추가</button>
                </div>

                    <div className={classes.topBox}>
                        {groupArray && (
                                <FormControl color="secondary" className={classes.formControl}>
                                    <InputLabel color="secondary" >분류</InputLabel>
                                    <Select
                                        value={board.group_number}
                                        onChange={onChangeGroup}
                                    >
                                        {groupArray.map((item, index) => (
                                            <MenuItem  key={item} value={index}>{item}</MenuItem>
                                        ))
                                        }
                                    </Select>
                                </FormControl>
                        )
                        }
                            {conditionArray && (
                                <FormControl color="secondary" className={classes.formControl}>
                                    <InputLabel color="secondary" >상태</InputLabel>
                                    <Select
                                    value={board.condition_number}
                                    onChange={onChangeCondition}
                                    >
                                    {conditionArray.map((item, index) => (
                                        <MenuItem  key={item} value={index}>{item}</MenuItem>
                                    ))
                                    }
                                    </Select>
                                </FormControl>
                            )
                            }
                        {board.files && (
                            <div className={classes.fileBox}>
                                <label className={classes.label}>첨부파일</label>
                                <span>{board.files.name}</span>
                            </div>
                            )}
                    </div>
                <div id="editor"></div>
                <Button type="submit" variant="contained"  color="primary" className={classes.button}>저장</Button>
            </form>
        </Fragment>
    );
};

export default BoardEditor;