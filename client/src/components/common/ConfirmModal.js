import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

const ConfirmModal = ({open, onClose, children}) => {

    return (
        <div>
            <Dialog
                open={open}
                onClose={onClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                {/*<DialogTitle id="alert-dialog-title">*/}
                {/*    완료*/}
                {/*</DialogTitle>*/}
                <DialogContent>
                    {/*<DialogContentText id="alert-dialog-description">*/}
                    {/*    Let Google help apps determine location. This means sending anonymous location data to*/}
                    {/*    Google, even when no apps are running.*/}
                    {/*</DialogContentText>*/}

                    {children !== undefined ? (children) : ("완료")}
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose} color="primary" autoFocus>
                        닫기
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default ConfirmModal;