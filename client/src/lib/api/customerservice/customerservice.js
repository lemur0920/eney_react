import client from '../client';
import qs from "qs";

export const boardList = ({type,page}) => {
    const queryString = qs.stringify({
        type, page
    });
    return client.get(`/board?${queryString}`)
}

export const faqBoardList = ({type,page}) => {
    const queryString = qs.stringify({
        type, page
    });
    return client.get(`/board?${queryString}`)
}

export const board = ({type,id}) => {
    const queryString = qs.stringify({
        type, id
    });
    return client.get(`/board/view?${queryString}`)
}

export const boardInfo = ({type}) => {
    const queryString = qs.stringify({
        type
    });
    return client.get(`/board/boardInfo?${queryString}`)
}

export const write = ({boardItem,content,type}) => {

    let formData = new FormData();
    formData.append("title", boardItem.title);
    formData.append("content", content);
    formData.append("group_number", boardItem.group_number);
    formData.append("condition_number", boardItem.condition_number);

    if(boardItem.files && boardItem.files.type){
        formData.append("files", boardItem.files);
    }

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return client.post(`/board/${type}/write`, formData,config);
}

export const update = ({boardItem,content,type}) => {

    let formData = new FormData();
    formData.append("content_id", boardItem.content_id);
    formData.append("title", boardItem.title);
    formData.append("content", content);
    formData.append("group_number", boardItem.group_number);
    formData.append("condition_number", boardItem.condition_number);

    if(boardItem.files && boardItem.files.type){
        formData.append("files", boardItem.files);
    }

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return client.post(`/board/${type}/update`, formData,config);
}

export const boardDelete = ({boardItem}) =>{

    return client.delete(`/board/delete/${boardItem.content_id}`,);
}