import client from '../client';
import qs from "qs";

export const getCategoryList = () => {
    console.log('in');
    return client.get(`/service/kakao/category/all`)
};

export const getAuthToken = ({yellowId, phoneNumber}) => {

    const queryString = qs.stringify({
        yellowId, phoneNumber
    });

    console.log(queryString);
    return client.get(`/service/kakao/sender/token?${queryString}`)
};


export const createPlusFriend = ({yellowId, phoneNumber, token, categoryCode}) => {

    const queryString = qs.stringify({
        yellowId, phoneNumber, token, categoryCode
    });

    console.log(queryString);
    return client.post(`/service/kakao/sender/create?${queryString}`)
};

export const deletePlusFriend = (senderKey) => {

    const queryString = qs.stringify({
        senderKey
    });

    console.log(queryString);
    return client.post(`/service/kakao/sender/delete?${queryString}`)
};


export const createKakaoTemplate = ({senderKey, uuid, name, templateName, templateContent, buttons}) => {

    let data = {
        senderKey,
        uuid,
        name,
        templateName,
        templateContent,
        buttons,
    }

    return client.post(`/service/kakao/template/create`, data);
    // return client.post(`/service/kakao/template/create`, formData, config);
};
export const updateKakaoTemplate = ({senderKey, templateCode,  uuid, name, templateName, templateContent, buttons}) => {

    let data = {
        senderKey,
        templateCode,
        uuid,
        name,
        templateName,
        templateContent,
        buttons,
    }

    return client.post(`/service/kakao/template/update`, data);
    // return client.post(`/service/kakao/template/create`, formData, config);
};

export const deleteKakaoTemplate = ({senderKey, templateCode}) => {

    const queryString = qs.stringify({
        senderKey,
        templateCode,
    });

    return client.post(`/service/kakao/template/delete?${queryString}`);

};


export const getSenderProfileList = ({page}) => {

    const queryString = qs.stringify({page});

    console.log(queryString);
    return client.get(`/service/kakao/sender/profileList?${queryString}`)
};


export const getKakaoTemplateList = ({page}) => {

    const queryString = qs.stringify({page});

    console.log(queryString);
    return client.get(`/service/kakao/template/list?${queryString}`)
};

export const getKakaoTemplate = ({senderKey, templateCode}) => {

    const queryString = qs.stringify({
        senderKey, templateCode
    });

    console.log(queryString);
    return client.get(`/service/kakao/template?${queryString}`)
};