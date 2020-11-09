import React,{useEffect} from 'react';
import {withRouter} from 'react-router-dom'
import ColoringApplyForm from "../../components/coloring_apply/ColoringApplyForm";
import {useDispatch, useSelector} from "react-redux";
import {registerColoringApply, initialize} from "../../modules/admin/coloring/coloring";
import swal from 'sweetalert';

const ColoringApply = ({history,cx}) => {

    const dispatch = useDispatch();

    const {registerResult, error, loading} = useSelector(({coloring,loading}) =>({
        registerResult: coloring.registerResult,
        error: coloring.error,
        loading: loading['coloring/REGISTER_COLORING_APPLY'],
    }))

    useEffect(() => {
        return () => {
            dispatch(initialize());
        }
    },[])

    useEffect(() => {
        if(registerResult && error === null){
            swal("신청이 완료되었습니다");
            history.push("/")
            dispatch(initialize());
        }else if(!registerResult && error !== null){
            swal(error);
        }
    },[registerResult,error])


    const onSubmit = (form) => {
        swal({
            text: "신청시 3만포인트가 차감됩니다. 진행하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
            .then((willDelete) => {
                if (willDelete) {
                    dispatch(registerColoringApply(form))
                }
            });
    }

    const cancelApply = () => {
        swal({
            text: "취소하시겠습니까?",
            buttons: ["취소", "확인"],
            closeOnConfirm: false,
        })
        .then((willDelete) => {
            if (willDelete) {
                history.push("/")
            }
        });
    }

    return (
        <ColoringApplyForm cx={cx} onSubmit={onSubmit} cancelApply={cancelApply}/>
    );
};

export default withRouter(ColoringApply);