import {createAction, handleActions} from 'redux-actions';
import produce from "immer";

const TOGGLE_SIDEBAR  = 'layout/TOGGLE_SIDEBAR';

export const toggleSidebar = createAction(TOGGLE_SIDEBAR);

const initialState = {
    isSidebarOpened: true
};

const layout = handleActions(
    {
        [TOGGLE_SIDEBAR]: (state) =>
            produce(state, draft => {
                draft.isSidebarOpened = !draft.isSidebarOpened
            })
    }, initialState
);
export default layout;