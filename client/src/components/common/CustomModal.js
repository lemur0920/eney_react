import React from 'react';
import Backdrop from "@material-ui/core/Backdrop/Backdrop";
import Modal from "@material-ui/core/Modal";
import {makeStyles} from "@material-ui/core";
import palette from "../../lib/styles/palette";
import Fade from "@material-ui/core/Fade";
// Tap event required
// import injectTapEventPlugin from 'react-tap-event-plugin';
// injectTapEventPlugin();


const useStyles = makeStyles(theme => ({
    modalBox: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        padding:"40px"
    },
    title:{
        textAlign:'left',
        marginBottom:15
    },
    sutTitle:{
        color:"gray",
        marginBottom:30,
        textAlign:'left'
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing(3, 5, 4),
        '& h2': {
            fontSize: '18px',
        }
    },
}));

const CustomModal = ({open, onClose, children, title, subTitle,error, style}) => {
    const classes = useStyles();

    return (
        <Modal
            disablePortal
            disableEnforceFocus
            disableAutoFocus
            disableBackdropClick
            className={classes.modalBox}
            open={open}
            onClose={onClose}
            closeAfterTransition
            BackdropComponent={Backdrop}
            BackdropProps={{
                timeout: 500,
            }}
            // disableBackdropClick
        >
            <Fade in={open}>
                <div className={classes.paper} style={style}>
                    <h2 className={classes.title}>{title}</h2>
                    {subTitle !== "" && (
                        <p className={classes.sutTitle}>{subTitle}</p>
                    )}
                    {children}
                </div>
            </Fade>
        </Modal>
    );
};

export default React.memo(CustomModal);