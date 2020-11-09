import React,{useEffect} from 'react';

const IamPortScript = () => {

    useEffect(() => {
        let w = window;
        let s = document.createElement('script');
        s.type = 'text/javascript';
        s.async = true;
        s.src = 'https://cdn.iamport.kr/js/iamport.payment-1.1.7.js';
        s.charset = 'UTF-8';

        let j = document.createElement('script');
        j.type = 'text/javascript';
        j.async = true;
        j.src = 'https://code.jquery.com/jquery-1.12.4.min.js';
        j.charset = 'UTF-8';


        let x = document.getElementsByTagName('script')[0];
        let y = document.getElementsByTagName('script')[1];
        x.parentNode.insertBefore(s, x);
        y.parentNode.insertBefore(j, y);

        console.log(x)
        console.log(y)

        return () => {
            x.parentNode.removeChild(s);
            y.parentNode.removeChild(y);


        }
    },[])

    return (
        null
    );
};

export default IamPortScript;