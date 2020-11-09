import React from 'react';
import Backdrop from "@material-ui/core/Backdrop/Backdrop";
import Modal from "@material-ui/core/Modal";
import {makeStyles} from "@material-ui/core";
import palette from "../../lib/styles/palette";
import Typography from "@material-ui/core/Typography";
// import Fade from "@material-ui/core/Fade";


const useStyles = makeStyles(theme => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        textAlign:"center",
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
    btn:{
        padding: "10px 20px",
        borderRadius: 5,
        marginTop:"20px",
        backgroundColor: palette.cyan[0],
        color:"white",
        '&:hover': {
            fontWeight: "bold",
            backgroundColor:palette.cyan[1]
        }
    },
}));


const CustomAlertModal = ({open, onClose, text,callBack}) => {
    const classes = useStyles();

    const rootRef = React.useRef(null);


    return (
        <Modal
            disablePortal
            disableEnforceFocus
            disableAutoFocus
            className={classes.modal}
            open={open}
            onClose={onClose}
            closeAfterTransition
            BackdropComponent={Backdrop}
            BackdropProps={{
                timeout: 500,
            }}

            container={() => rootRef.current}
        >

            <div className={classes.paper}>
                <Typography>{text}</Typography>
                <button className={classes.btn} type="button" onClick={() => callBack()}>
                    닫기
                </button>
            </div>

        </Modal>
    );
};

export default React.memo(CustomAlertModal);