import React, {Fragment, useRef, useState} from 'react';
import coloringInfo from './ColoringData';
import Button from "../common/Button";
import FormControl from "@material-ui/core/FormControl";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Radio from "@material-ui/core/Radio";
import {FormGroup} from "@material-ui/core";
import styled from "styled-components";

const CustomCancelButton = styled(Button)`
      padding: 6px 16px;
    font-size: 0.875rem;
    margin: 8px;
  background-color: white;
  color:#37afe5;
  border:1px solid #37afe5;
      font-weight: 500;
    line-height: 1.75;
  &:hover{
    background: none;
  
`;


const ColoringApplyForm = ({onSubmit,cancelApply,cx}) => {

    const [selectedSound, setSelectedSound] = useState({
        voice_tone:"",
        bgm:"",
        script:""
    })

    const [selectedMent, setSelectedMent] = useState(null)
    const [playingTone, setPlayingTone] = useState("")
    const [playingBgm, setPlayingBgm] = useState("")

    // const [selectedTone, setSeletedTone] = useState({
    //     name:"",
    //     file:""
    // })

    const coloringForm = useRef()
    const toneAudio = useRef()
    const bgmAudio = useRef()

    const changeSelectedItem = (e) => {
        const {name, value} = e.target;
        setSelectedSound({
            ...selectedSound,
            [name] : value
        })
    }

    const setMent = (e) => {
        setSelectedMent(e.target.value)

        setSelectedSound({
            ...selectedSound,
            script: coloringInfo.ment[parseInt(e.target.value)].text
        })
    }

    const tonePlaying = (file) =>{
        setPlayingTone(file)
        if(toneAudio.current.paused){
            toneAudio.current.load();
            toneAudio.current.play();
        }else if(!toneAudio.current.paused && file !== playingTone){
            toneAudio.current.load();
            toneAudio.current.play();
        }else {
            setPlayingTone("")
            toneAudio.current.load();
            toneAudio.current.pause();
        }
    }

    const bgmPlaying = (file) =>{
        setPlayingBgm(file)
        if(bgmAudio.current.paused){
            bgmAudio.current.load();
            bgmAudio.current.play();
        }else if(!bgmAudio.current.paused && file !== playingBgm){
            bgmAudio.current.load();
            bgmAudio.current.play();
        }else {
            setPlayingBgm("")
            bgmAudio.current.load();
            bgmAudio.current.pause();
        }
    }

    return (
        <Fragment>
            <audio ref={toneAudio}>
                <source src={playingTone !== "" ? require(`../../asset/audio/tone/${playingTone}`) : ""} type="audio/mp3" />
            </audio>
            <audio ref={bgmAudio}>
                <source src={playingBgm !== "" ? require(`../../asset/audio/bgm/${playingBgm}`) : ""} type="audio/mp3" />
            </audio>
            <form className={cx("coloring_apply_form")} ref={coloringForm} onSubmit={(e) => {e.preventDefault(); onSubmit(selectedSound);}}>
                <table className={cx("coloring_apply_table")}>
                    <colgroup>
                        <col style={{width:"14%"}}/>
                        <col style={{width:"86%"}}/>
                    </colgroup>
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td align="center">시나리오 샘플</td>
                        <td>
                            {coloringInfo.ment.map((item, index) => (
                                <Button type="button" className={cx(`${index === parseInt(selectedMent) ? "on" : "off"}`)} eney value={index} onClick={(e) => {setMent(e)}} key={index}>{item.name}</Button>
                            ))}
                        </td>
                    </tr>
                    <tr>
                        <td align="center">시나리오</td>
                        <td>
                            <textarea placeholder="" name="script" required={true} value={selectedSound.script} onChange={ e => {changeSelectedItem(e)}}/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">목소리 톤</td>
                        <td>
                            <Fragment>
                                <FormControl >
                                    <RadioGroup className={cx("radio_group")} aria-label="type" name="voice_tone" value={selectedSound.voice_tone} onChange={ e => {changeSelectedItem(e)}}>
                                        {coloringInfo.tone.map((item, index) => (
                                            <Fragment key={index} >
                                            <FormControlLabel  className={cx("inputLabel")} value={item.name} control={<Radio required={true} color="primary"/>} label={item.name} />
                                                <button type="button" className={cx(`${ toneAudio.current !== undefined && !toneAudio.current.paused && playingTone === item.file ? "playing" : "paused"}`)} onClick={() => tonePlaying(item.file)}>
                                                </button>
                                            </Fragment>
                                        ))}
                                    </RadioGroup>
                                </FormControl>
                            </Fragment>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">배경음악</td>
                        <td>
                            <Fragment>
                                <FormControl >
                                    <RadioGroup required={true} className={cx("radio_group")} aria-label="type" name="bgm" value={selectedSound.bgm} onChange={ e => {changeSelectedItem(e)}}>
                                        {coloringInfo.bgm.map((item, index) => (
                                            <Fragment key={index} >
                                                <FormControlLabel  className={cx("inputLabel")} value={item.name} control={<Radio required={true} color="primary"/>} label={item.name} />
                                                <button type="button" className={cx(`${ bgmAudio.current !== undefined && !bgmAudio.current.paused && playingBgm === item.file ? "playing" : "paused"}`)} onClick={() => bgmPlaying(item.file)}>
                                                </button>
                                            </Fragment>
                                        ))}
                                    </RadioGroup>
                                </FormControl>
                            </Fragment>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div className={cx("submit_box")}>
                    <FormGroup row className={cx("agree_box")}>
                        <input type='checkbox'
                            // checked={checkAll}
                            // checked={state.checkedB}
                            //    onChange={allCheck}
                            //    value={true}
                            // className={classes.checkBox}
                               color="primary"
                               id="all_chk"
                               required={true}
                        />
                        <label htmlFor="all_chk">작성한 시나리오 내용으로 음원 제작 신청을 동의합니다.</label>
                    </FormGroup>
                    <Button eney className={cx("submit_btn")} onClick={(e) => {console.log("신청")}}>신청</Button><CustomCancelButton type="button" className={cx("submit_btn")} onClick={() => cancelApply()}>취소</CustomCancelButton>
                </div>
            </form>

        </Fragment>
    );
};

export default ColoringApplyForm;