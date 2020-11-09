import client from '../client';
import qs from "qs";

export const getTemplateList = (page) => {
    const queryString = qs.stringify({
        page
    });

    console.log('queryString :: ' + queryString)
    return client.get(`/service/message/template?${queryString}`)
};

export const getTemplateDetail = (idx) => {
    console.log(idx);
    const queryString = qs.stringify({
        idx
    });
    console.log(queryString);
    return client.get(`/service/message/templateDetail?${queryString}`)
};

    export const deleteTemplate = (idx) => {
    console.log(idx);
    const queryString = qs.stringify({
        idx
    });
    console.log(queryString);
    return client.post(`/service/message/template/delete?${queryString}`)
};


export const updateTemplate = ({idx, subject, text}) => {
    const queryString = qs.stringify({
        idx, subject, text

    });
    console.log(queryString);

    return client.put(`/service/message/template?${queryString}`)
}

export const createTemplate = ({subject, text, msg_type}) => {
    //
    const form = new FormData();
    form.append("subject", subject);
    form.append("text", text);
    form.append("msg_type", msg_type);

    // const form = {
    //     subject,
    //     text,
    //     msg_type
    // };

    console.log(form);

    return client.post('/service/message/template', form);
};