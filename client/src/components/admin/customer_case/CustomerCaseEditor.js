import React, {Fragment, useEffect,useState} from 'react';
import Input from "@material-ui/core/Input";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import Button from "@material-ui/core/Button";
import TableIcon from "../../../asset/image/svg/icon.svg";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from 'moment';
import style from '../../../asset/style/admin/customer_case.module.css';
import classnames from "classnames/bind";
import styled from "styled-components";
import Dialog from "@material-ui/core/Dialog";
import IconButton from "@material-ui/core/IconButton";
import * as clientLib from '../../../lib/api/client';
import swal from 'sweetalert';

const cx = classnames.bind(style);


const SaveBtn = styled(Button)`
  float: right;
  vertical-align: baseline;
  margin-right: 30px;
  color:white;
  width:110px;
`;

const CustomerCaseEditor = ({cateList,onSubmit,type, item}) => {

    let quill;
    let Quill = null;


    const [form, setForm] = useState({
        clients: item !== null ? item.clients : "",
        project:"",
        description:"",
        type_code: 1,
        link:"",
        release_date: new Date(),
        tumbnail:null,
        tumbnail_file:"",
    })

    const [videoUrl, setVideoUrl] = useState("");

    const [content, setContent] = useState("")
    const [isDelete, setIsDelete] = useState(false)
    const [isOpen, setIsOpen] = useState(false);

    useEffect(() => {
        if(item !== null && type == "edit"){
            console.log(moment(item.release_date).format('YYYY-MM-DD'))
            setForm({
                ...form,
                clients:item.clients,
                project:item.project,
                description:item.description,
                type_code:item.type_code,
                link:item.link,
                release_date: new Date(item.release_date),
                tumbnail_file:item.tumbnail_file,
            })
        }
    },[item])

    const onChange = (e) => {
        const {value, name} = e.target
        setForm({
            ...form,
            [name]: e.target.name === "tumbnail" ? e.target.files[0] : value
        })

    }

    const insertVideo = () => {

        const range = window.quill.getSelection(true);
        window.quill.insertEmbed(range.index, 'video', videoUrl);
        setVideoUrl("");
    }


    const openTumbnail = () => {
        setIsOpen(!isOpen)
    }

    const handleTumbnailDel = () => {
        swal({
            text: "삭제하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                setIsDelete(true)
            }
        });

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
            xhr.open('POST', `${clientLib.getUrl()}/file/imageUpload`, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    let response = xhr.response;
                    console.log(response)
                    if (response !== '') {
                        // Save current cursor state
                        const range = window.quill.getSelection(true);

                        window.quill.setSelection(range.index + 1);
                        window.quill.deleteText(range.index, 1);
                        window.quill.insertEmbed(range.index, 'image', `${clientLib.getUrl()}/file/image/`+response);
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
                window.quill = new Quill('#customer_case_edit', {
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

                    image: imageHandler,
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
            window.quill.clipboard.dangerouslyPasteHTML(0, "");
        }

    },[])
    const handle = (e) => {
        setContent(window.quill.container.firstChild.innerHTML);
    }

    useEffect(() => {

        if(item !== null && type == "edit"){
            window.quill.clipboard.dangerouslyPasteHTML(0, item.content);
        }

    },[item])

    return (
        <Fragment>
            {/*<p style={{color:"red"}}>{error}</p>*/}
            <form className={cx("customer_case_add_form")} name="customerCaseForm" onSubmit={(e) => {e.preventDefault();onSubmit()}}>
                <div className={cx("input_box")}>
                    <label className={cx("label")} htmlFor="clientsInput">Clients</label>
                    <Input color="secondary" className={cx("input")} id="clientsInput" type="text" name="clients"
                           onChange={onChange}
                           value={form.clients}
                           required
                        />
                    <label className={cx("label")} htmlFor="projectInput">Project</label>
                    <Input color="secondary" className={cx("input")} id="projectInput" type="text" name="project"
                        onChange={onChange}
                           value={form.project}
                           required
                    />
                    {/*<label className={cx("label")} htmlFor="linkInput">Link</label>*/}
                    {/*<Input color="secondary" className={cx("input")} id="linkInput" type="text" name="link"*/}
                    {/*    onChange={onChange}*/}
                    {/*       value={form.link}*/}
                    {/*       placeholder="https://........"*/}
                    {/*       required*/}
                    {/*/>*/}
                <br/>
                    <label className={cx("label")} htmlFor="fileInput">Tumbnail</label>
                    {type === "edit" && item !== null && !isDelete?
                        (
                            <Fragment>
                            <Button color="primary" variant="outlined" className={cx("manage_btn")} onClick={() => openTumbnail()}>확인</Button>
                            <Button color="default" variant="outlined" className={cx("manage_btn")} onClick={() => handleTumbnailDel()}>삭제</Button>
                            </Fragment>
                    //         <Input color="secondary" className={cx("input")} id="fileInput" type="file" name="tumbnail"
                    //                            onChange={onChange}
                    //                            required
                    // />
                        )
                        :  (
                            <Input color="secondary" className={cx("input")} name="tumbnail" id="fileInput" type="file"
                                                    onChange={onChange}
                                                    required
                                   accept="image/*"/>
                    )}
                    {cateList !== null && (
                        <FormControl color="secondary" required className={cx("selectBox")}>
                            <InputLabel color="secondary" >분류</InputLabel>
                            <Select
                                value={form.type_code}
                                onChange={onChange}
                                name="type_code"
                            >
                                {cateList.map((item, index) => (
                                    <MenuItem  key={item.code} value={item.code}>{item.code_name}</MenuItem>
                                ))
                                }
                            </Select>
                        </FormControl>
                    )
                    }
                    <label className={cx("label")} htmlFor="dateInput">ReleaseDate</label>
                    <DatePicker
                        id="dateInput"
                        // name="release_date"
                        type="date"
                        className={cx("input")}
                        selected={form.release_date}
                        onChange={(date) => {setForm({...form,release_date: date});}}
                        dateFormat="yyyy-MM-dd"
                    />
                    <input type="date " name="release_date" value={form.release_date} hidden readOnly={true}/>
                    <SaveBtn type="submit" variant="contained"  color="primary" className={""}>저장</SaveBtn>
                </div>
                <div className={cx("input_box","last_box")}>
                    <label className={cx("label")} htmlFor="descriptionInput">Description</label>
                    <Input color="secondary" className={cx("input","desc")} id="descriptionInput" type="text" name="description"
                           onChange={onChange}
                           value={form.description}
                    />
                </div>
                <div className={cx("input_box","last_box")}>
                    <label className={cx("label")} htmlFor="videoInput">Video Url</label>
                    <Input color="secondary" className={cx("input")} id="videoInput" type="text"
                           onChange={(e) => {setVideoUrl(e.target.value)}}
                           value={videoUrl}
                    />
                    <button type="button" onClick={() => insertVideo()} style={{"margin-left":"10px"}}>추가</button>
                    <span style={{"margin-left":"10px","color":"red"}}>iframe의 src만 넣어어주세요</span>
                </div>
                <div id="customer_case_edit" className={cx("customer_case_editor")} onBlur={() => handle()}></div>
                <input type="text " name="content" value={content} hidden readOnly={true}/>
                <input type="number " name="idx" value={type === "edit" && item !== null? item.idx : 0} hidden readOnly={true}/>
            </form>
            {isOpen && (
                <Dialog fullScreen open={isOpen} onClose={() => openTumbnail()}>
                    <IconButton  className={cx("modal_close_btn")} color="inherit"  onClick={() => openTumbnail()} aria-label="close">
                        {/*<CloseIcon />*/}
                        닫기
                    </IconButton>
                    <img
                        className={cx("image")}
                        src={`${clientLib.getUrl()}/customer_case/tumbnail/${item.tumbnail_file}`}
                        alt="no image"
                    />
                </Dialog>
            )}
        </Fragment>
    );
};

export default CustomerCaseEditor;