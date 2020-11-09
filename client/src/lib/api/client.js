import axios from 'axios';

const prodUrl = 'https://eney.co.kr:4005';
export const getUrl = () =>{
    if(process.env.NODE_ENV === 'production'){
        return prodUrl;
    }else{
        return 'http://localhost:4000';
    }
}
const client = axios.create({
    //개발
    // baseURL : 'http://localhost:4000',
    //운영
    // baseURL : 'http://210.103.188.124:4005',

    baseURL: getUrl(),
    headers: {'Content-Type': 'application/json'},
})

client.interceptors.request.use (
    function (config) {
        const user = JSON.parse(localStorage.getItem("user"));
        if (user) config.headers.Authorization = `Bearer ${user.accessToken}`;
        return config;
    },
    function (error) {
        return Promise.reject (error);
    }
);

export default client;