import React,{useEffect} from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter,StaticRouter} from 'react-router-dom';
import {Provider} from 'react-redux'
import {createStore, applyMiddleware} from 'redux';
import {composeWithDevTools} from 'redux-devtools-extension';
import createSagaMiddleware from 'redux-saga'
import rootReducer,{rootSaga} from './modules';
import {tempSetUser,check} from "./modules/auth";
import './asset/style/commons.css'
import App from './App';
import * as serviceWorker from './serviceWorker';
import Themes from "./themes";
import CssBaseline from "@material-ui/core/CssBaseline";
import { ThemeProvider } from '@material-ui/core/styles';
import { createBrowserHistory,createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { loadableReady } from '@loadable/component';
// IE11의 경우
import 'core-js/stable';
import 'regenerator-runtime/runtime';
import 'react-app-polyfill/ie11';
import 'react-app-polyfill/stable';
import jQuery from "jquery";
window.$ = window.jQuery = jQuery;
const isBrowser = process.env.APP_ENV === 'browser';


// export const customHistory = createBrowserHistory();

let customHistory;

if (isBrowser){
    customHistory = createBrowserHistory();
}
else {
    customHistory = createMemoryHistory(); //This kind of history is needed for server-side rendering.
}

const sagaMiddleware = createSagaMiddleware({
    context: {
        history: customHistory
    }
});

// const store = createStore(rootReducer, composeWithDevTools(applyMiddleware(sagaMiddleware)))

const store = createStore(
    rootReducer,
    window.__PRELOADED_STATE__, // 이 값을 초기상태로 사용함
    composeWithDevTools(applyMiddleware(sagaMiddleware))
);

function loadUser(){

    try{
        const user = localStorage.getItem("user");
        if(!user)return;

        store.dispatch(tempSetUser(user));
        store.dispatch(check());
    }catch(e){
        console.log("localStorage is not working")
    }
}

sagaMiddleware.run(rootSaga)
// loadUser();

const Root = () => {
    loadUser();


    useEffect( () => {
        const jssStyles = document.querySelector('#jss-server-side');
        if (jssStyles) {
            jssStyles.parentElement.removeChild(jssStyles);
        }


    },[])



    return (
        <Router history={customHistory}>
            <Provider store={store}>
                {/*<BrowserRouter>*/}
                    <ThemeProvider theme={Themes.default}>
                        <CssBaseline />
                        <App />
                    </ThemeProvider>
                {/*</BrowserRouter>*/}
            </Provider>
        </Router>
    );
};


const root = document.getElementById('root');

if (process.env.NODE_ENV === 'production') {
    loadableReady(() => {
            ReactDOM.hydrate(<Root />, root);
    });
} else {
        ReactDOM.render(<Root/>, root);
}

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
