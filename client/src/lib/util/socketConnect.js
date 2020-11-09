// import socketIOClient from 'socket.io-client';
// // import SockJS from 'sockjs-client'
// // // let sock = new SockJS('http://localhost:8080/api/ws');
// //
// // // sock.onopen = function() {
// // //     console.log('open');
// // //     sock.send('test');
// // // };
// // let stompClient = null;
// //
// // export const getConnect = () =>{
// //     let sock = new SockJS('http://localhost:8080/api/ws');
// //
// //
// //     // const socket = socketIOClient('/api/payment_socket');
// //     //
// //     // socket.on('connect', data => {
// //     //     console.log(data);
// //     //     socket.emit('reply','Hellow Node.js')
// //     // })
// //
// //     // const socket = socketIOClient.connect('http://localhost:8080/api/ws');
// //     // (() => {
// //     //     socket.emit('init', { name: 'bella' });
// //     //
// //     //     socket.on('welcome', (msg) => {
// //     //         console.log(msg);
// //     //     });
// //     // })();
// //
// //
// // }
define(function(require) {
    'use strict';

    let SockJS = require('sockjs-client');
    require('stomp-websocket');

    let stompClient;

    //운영
    const socket = SockJS('https://eney.co.kr/server/ws');

    //개발
    // const socket = SockJS('http://localhost:4000/ws');

    return {
        register: register,
        close: close,
    };

    function register(registrations) {
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            registrations.forEach(function (registration) {
                stompClient.subscribe(registration.route, registration.callback);
            });
        });
    }


    function close() {
        console.log("close!!!!")
        socket.close();

    }

});