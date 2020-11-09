import React,{useEffect,useState} from 'react';
import {Route,Redirect,Switch,Router } from "react-router-dom";
import {withRouter} from 'react-router'
import loadable from '@loadable/component'
const FontFaceObserver = require('fontfaceobserver');
const ScrollTop = loadable(() => import('./components/common/ScrollTop'))
const isBrowser = process.env.APP_ENV === 'browser';

const AdminPageRoute = loadable(() => import("./pages/PageRoute/AdminPageRoute"));
const HomePageRoute = loadable(() => import("./pages/PageRoute/HomePageRoute"))
const ServicePageRoute = loadable(() => import("./pages/PageRoute/ServicePageRoute"))
const AuthPageRoute = loadable(() => import("./pages/PageRoute/AuthPageRoute"))
import HelmetController from "./lib/helmet/HelmetController";






function App() {

    useEffect(() => {

        if(isBrowser) {
            const EVENTS_TO_MODIFY = ['touchstart', 'touchmove', 'touchend', 'touchcancel', 'wheel'];

            const originalAddEventListener = document.addEventListener.bind();
            document.addEventListener = (type, listener, options, wantsUntrusted) => {
                let modOptions = options;
                if (EVENTS_TO_MODIFY.includes(type)) {
                    if (typeof options === 'boolean') {
                        modOptions = {
                            capture: options,
                            passive: false,
                        };
                    } else if (typeof options === 'object') {
                        modOptions = {
                            passive: false,
                            ...options,
                        };
                    }
                }

                return originalAddEventListener(type, listener, modOptions, wantsUntrusted);
            };

            const originalRemoveEventListener = document.removeEventListener.bind();
            document.removeEventListener = (type, listener, options) => {
                let modOptions = options;
                if (EVENTS_TO_MODIFY.includes(type)) {
                    if (typeof options === 'boolean') {
                        modOptions = {
                            capture: options,
                            passive: false,
                        };
                    } else if (typeof options === 'object') {
                        modOptions = {
                            passive: false,
                            ...options,
                        };
                    }
                }
                return originalRemoveEventListener(type, listener, modOptions);
            };
        }

        let font = new FontFaceObserver('noto');

        font.load().then(function () {
            document.body.classList.add('fonts-loaded')
        });

    },[])


  return (
        <div className="App">
            <HelmetController/>
                <ScrollTop>
                    <Switch>
                        <Route
                            exact
                            path="/admin"
                            render={() => <Redirect to="/admin/dashboard" />}
                        />
                        <Route path="/admin" component={AdminPageRoute}/>
                        <Route
                            exact
                            path="/service"
                            render={() => <Redirect to="/service/patchcall/management" />}
                        />
                        <Route path="/service" component={ServicePageRoute}/>
                        <Route path="/auth" component={AuthPageRoute}/>
                        <Route path="/" component={HomePageRoute}/>
                    </Switch>
                </ScrollTop>
        </div>
  );
}

export default withRouter(App);
