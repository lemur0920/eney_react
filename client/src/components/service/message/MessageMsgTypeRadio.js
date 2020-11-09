import React, {Component, Fragment, useEffect, useState} from 'react';
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import FormControl from "@material-ui/core/FormControl";
import coloringInfo from "../../coloring_apply/ColoringData";
import {makeStyles} from "@material-ui/core/styles";


const MESSAGE_TYPE = [
    {type: 'SMS', code: '1'},
    {type: 'LMS/MMS', code: '3'},
]

const useStyles = makeStyles(theme => ({
    setFont: {
        fontFamily: "noto",
    }
}));


const MessageMsgTypeRadio = ({cx, changeMsg_type, msg_type}) => {

    const classes = useStyles();


    const [selectMsgType, setSelectMsgType] = useState('1');

    const changeSelectedItem = (e) => {
        const {name, value} = e.target;
        setSelectMsgType(value);

    };

    /*useEffect(() => {

        console.log('Works!');
        console.log(msg_type);
        setSelectMsgType(msg_type);

    })*/


    return (
        <Fragment>


            {/*<div className={cx("section_file")}>*/}
            {/*    <ul>*/}
            {/*        <li>*/}
            {/*<div className={cx("btn_name")}>*/}

            <FormControl>
                <RadioGroup className={cx("radio_group")} aria-label="type" name="msg_type"
                            value={msg_type} onChange={e => {
                    changeMsg_type(e)
                }}>

                    <ul className={cx("clfx")}>

                        {MESSAGE_TYPE.map((item, index) => (
                            <Fragment key={index}>
                                <FormControlLabel value={item.code}
                                                  control={<Radio required={true} color="primary"/>}
                                                  label={item.type}/>
                            </Fragment>

                        ))}
                    </ul>
                </RadioGroup>

            </FormControl>

            {/*</div>*/}
            {/*</li>*/}
            {/*</ul>*/}
            {/*</div>*/}

        </Fragment>
    );
}

export default MessageMsgTypeRadio;