import axios from 'axios';
import * as clientLib from '../../../lib/api/client';

export default class ImageUploadAdapter {
    constructor( loader ) {
        // CKEditor 5's FileLoader instance.
        this.loader = loader;

        // URL where to send files.
        this.url = `${clientLib.getUrl()}file/imageUpload`;
    }

    // Starts the upload process.
    upload() {
        return new Promise( ( resolve, reject ) => {
            this._initRequest();
            this._initListeners( resolve, reject );
            this._sendRequest();
        } );
    }

    // Aborts the upload process.
    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    // Example implementation using XMLHttpRequest.
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();

        xhr.open( 'POST', this.url, true );
        xhr.responseType = 'json';
    }

    _initListeners( resolve, reject ) {
            this.loader.file.then(response => {
                let file = response

                const data = new FormData();
                // data.append('typeOption', 'upload_image');
                data.append('files', file);

                return axios({
                    url: `${clientLib.getUrl()}/file/imageUpload`,
                    method: 'post',
                    data,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    withCredentials: true
                }).then(res => {
                    resolve( {
                        default: response.data
                    } );
                }).catch(error => {
                    reject(error)
                });

            })

        // const xhr = this.xhr;
        // const loader = this.loader;
        // const genericErrorText = 'Couldn\'t upload file:' + ` ${ loader.file.name }.`;
        //
        // xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        // xhr.addEventListener( 'abort', () => reject() );
        // xhr.addEventListener( 'load', () => {
        //     const response = xhr.response;
        //
        //     console.log("$$$")
        //     console.log(response)
        //
        //
        //     if ( !response || response.error ) {
        //         return reject( response && response.error ? response.error.message : genericErrorText );
        //     }
        //
        //     // If the upload is successful, resolve the upload promise with an object containing
        //     // at least the "default" URL, pointing to the image on the server.
        //     resolve( {
        //         default: response.data
        //     } );
        // } );
        //
        // if ( xhr.upload ) {
        //     xhr.upload.addEventListener( 'progress', evt => {
        //         if ( evt.lengthComputable ) {
        //             loader.uploadTotal = evt.total;
        //             loader.uploaded = evt.loaded;
        //         }
        //     } );
        // }
    }

    // Prepares the data and sends the request.
    _sendRequest() {
        this.loader.file.then(response => {
            const data = new FormData();

            data.append( 'files', response );

            this.xhr.send( data );


        })
        // const data = new FormData();
        //
        //
        // data.append( 'files', this.loader.file );
        //
        // this.xhr.send( data );
    }

    // constructor( loader ) {
    //     this.loader = loader;
    //     this.upload = this.upload.bind(this)
    //     this.abort = this.abort.bind(this)
    // }
    //
    // upload() {
    //
    //     this.loader.file.then(response => {
    //         let file = response
    //
    //         const data = new FormData();
    //         // data.append('typeOption', 'upload_image');
    //         data.append('files', file);
    //
    //         return axios({
    //             url: `http://localhost:8080/api/file/imageUpload`,
    //             method: 'post',
    //             data,
    //             headers: {
    //                 'Content-Type': 'multipart/form-data'
    //             },
    //             withCredentials: true
    //         }).then(res => {
    //             // console.log(res)
    //             // let resData = {default:""};
    //             // resData.default = res.data;
    //             // // return resData;
    //             var resData = {};
    //             resData.default = res.data;
    //
    //             console.log("###")
    //             console.log(resData)
    //             return resData;
    //
    //             // return {
    //             //     default:res.data
    //             // }
    //         }).catch(error => {
    //             console.log(error)
    //             console.log("발생")
    //             return Promise.reject(error)
    //         });
    //
    //     })
    //     // const data = new FormData();
    //     // // data.append('typeOption', 'upload_image');
    //     // data.append('files', this.loader.file);
    //     //
    //     // console.log(this.loader.file)
    //     //
    //     // return axios({
    //     //     url: `http://localhost:8080/api/file/imageUpload`,
    //     //     method: 'post',
    //     //     data,
    //     //     headers: {
    //     //         'Content-Type': 'multipart/form-data'
    //     //     },
    //     //     withCredentials: true
    //     // }).then(res => {
    //     //     console.log(res)
    //     //     var resData = res.data;
    //     //     resData.default = resData.url;
    //     //     return resData;
    //     // }).catch(error => {
    //     //     console.log(error)
    //     //     return Promise.reject(error)
    //     // });
    // }
    //
    // abort() {
    //     // Reject promise returned from upload() method.
    // }
}  