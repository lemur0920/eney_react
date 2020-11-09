import React, {useState,useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import CustomModal from "../../../components/common/CustomModal";
import {Box, Button} from "@material-ui/core";
import {numberCheck, acsSubmit,checkAcsNumber,initialize} from "../../../modules/service/acs";
import PatchCallCidNumCheck from "../../../components/service/patchcall/cid/PatchCallCidNumCheck";
import PatchCallACS from "../../../components/service/patchcall/cid/PatchCallACS";
import swal from 'sweetalert';

const PatchCallCidAdd = ({open, onClose,cx}) => {
    const dispatch = useDispatch();

    const [num, setNum] = useState("");
    const [certifyNum, setCertifyNum] = useState("");
    const [incoming,setIncoming] = useState(false);

    const {acs, loading, acsCheck, acsError} = useSelector(({loading, acs}) =>({
        acs:acs.acs,
        acsCheck:acs.check,
        acsError:acs.error,
        loading: loading['acs/NUMBER_CHECK'],
    }))

    const title = "발신번호 등록";
    const subTitle = "";

    useEffect(() => {
        dispatch(initialize())
        return () => {
            dispatch(initialize())
        }
    },[])

    const onNumberCheck = () => {
        dispatch(numberCheck(num))
    }

    const onCheckAcsNumber= () => {
        const data = {
            acs_number:certifyNum,
            req_num:acs.req_num
        }
        dispatch(checkAcsNumber(data))
    }

    useEffect(() => {
        //번호 확인 성공
        if(acsCheck.numberCheck && acsError.numberCheck === null){
            swal({
                text: "사용 가능한 번호입니다.\n해당 번호로 인증번호를 수신하시겠습니까?",
                buttons: ["취소", "확인"],
                closeOnConfirm: false,
            })
                .then((willDelete) => {
                    if (willDelete) {
                        setIncoming(true)
                        dispatch(acsSubmit(num))
                    }else{
                        onClose();
                    }
                });
        }else if(!acsCheck.numberCheck && acsError.numberCheck !== null){
            swal("사용이 불가능한 번호입니다.");
            onClose();
        }

        //인증번호 발송 성공
        if(acsCheck.numberCheck && acsCheck.acsCheck && acsError.acs === null){
            swal("인증번호를 발송하였습니다.\n인증번호를 입력하고 인증 버튼을 눌러주세요.");
        }

        if(acsCheck.numberCheck && acsCheck.acsCheck && acsCheck.checkAcsNumberCheck && acsError.checkAcsNumberCheck === null){
            swal("성공적으로 발신번호 등록을 완료하였습니다.");
            onClose();
        }else if(!acsCheck.numberCheck && !acsCheck.acsCheck && !acsCheck.checkAcsNumberCheck && acsError.checkAcsNumberCheck !== null){
            swal("발신번호 등록이 실패했습니다. 다시 시도해주세요.");
            onClose();
        }

    },[acsCheck,acsError])

    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <Box>
                <div className={cx("cid_add_modal","tb_style_1")}>
                    <table>
                        <tbody>
                            <PatchCallCidNumCheck cx={cx} onNumberCheck={onNumberCheck}setNum={setNum}/>
                            {acsCheck.numberCheck && incoming && (
                                <PatchCallACS cx={cx}  onCheckAcsNumber={onCheckAcsNumber} setCertifyNum={setCertifyNum}/>
                            )}
                        <tr>
                            <td colSpan={2} style={{borderBottom:"0px"}}>
                                <p>
                                {acsError.numberCheck}
                                {acsError.acs}
                                {acsError.checkAcsNumberCheck}
                                </p>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <button type="button" className={cx("btn","closeBtn")} onClick={(e)=>{onClose();}}>닫기</button>
                </div>
            </Box>
        </CustomModal>
    );
};

export default PatchCallCidAdd;