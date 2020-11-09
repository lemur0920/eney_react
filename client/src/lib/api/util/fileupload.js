import client from '../client';
import qs from "qs";

export const imageUpload = (image) => {

    let formData = new FormData();
    formData.append("files", image);

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return client.post(`/file/imageUpload`, formData,config)
}

export const licenseUpload = (image) => {


    let formData = new FormData();
    formData.append("files", image);

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    };

    return client.post(`/user/mypage/company/license`, formData,config)
}