import React,{useState,useEffect} from 'react';
import {Box} from "@material-ui/core";
import PatchCallCidNumCheck from "../../../components/service/patchcall/cid/PatchCallCidNumCheck";
import PatchCallACS from "../../../components/service/patchcall/cid/PatchCallACS";
import CustomModal from "../../../components/common/CustomModal";
import Typography from "@material-ui/core/Typography";
import DialogContent from "@material-ui/core/DialogContent";

const AnalyticsViewIdAddModal = ({open, onClose,cx,onAddViewId,loading,result,error}) => {

    const title = "Google Analytics View Id 등록";
    const subTitle = "";

    const [data, setData] = useState({
        viewId : "",
        comments: ""
    })


    useEffect(() => {
        return () => {
            setData({
                viewId : "",
                comments: ""
            })
        }

    },[])

    const onChangeValue = (e) => {
        const {name, value} = e.target
        setData({
            ...data,
            [name]: value
        })

    }

    const clearData = () =>{
        setData({
            viewId : "",
            comments: ""
        })
    }


    return (
        <CustomModal open={open} onClose={onClose} title={title} subTitle={subTitle}>
            <Box>
                <div className={cx("view_id_add_modal","tb_style_1")}>
                    <form onSubmit={(e) =>{e.preventDefault(); onAddViewId(data);}}>
                        <table>
                            <tbody>
                            <tr>
                                <td>VIEW ID</td>
                                <td><input type="text" required={true} value={data.viewId} name="viewId" onChange={(e) =>onChangeValue(e)}/></td>
                            </tr>
                            <tr>
                                <td>Comments</td>
                                <td><input type="text" value={data.comments} name="comments" onChange={(e) =>onChangeValue(e)}/></td>
                            </tr>
                            <tr>
                                <td colSpan={2}>
                                    {!result && error !== null && (
                                        <Typography color="error">{error}</Typography>
                                    )}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <button type="submit" className={cx("btn","closeBtn")}>저장</button>
                        <button type="button" className={cx("btn","closeBtn")} onClick={(e)=>{onClose();clearData()}}>닫기</button>
                    </form>
                </div>
            </Box>
        </CustomModal>
    );
};

export default AnalyticsViewIdAddModal;