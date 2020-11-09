import React,{useState} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Typography from "@material-ui/core/Typography";
import ConfirmModal from "./ConfirmModal";

const AlertModal = ({open, onClick, onClose,title,content,error, result }) => {

    // const [confirm, setConfirm] = useState();
    return (
        <Dialog
            open={open}
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle >{title}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    {content}
                </DialogContentText>
                <Typography color="error">{error}</Typography>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="primary">
                    취소
                </Button>
                <Button onClick={onClick} color="primary" autoFocus>
                    확인
                </Button>
            </DialogActions>
            <ConfirmModal open={result} onClose={onClose}/>
        </Dialog>
    );
};

export default AlertModal;