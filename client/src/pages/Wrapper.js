import React,{Fragment} from 'react';
import {withRouter} from 'react-router'
import loadable from "@loadable/component";

const Header = loadable(() => import('../components/header/Header'))
const Footer = loadable(() => import('../components/footer/Footer'))

const Wrapper = ({children,location, history}) => {
    return (
        <Fragment>
            {/*<Header location={location} history={history}/>*/}
            <main>
                {children}
            </main>
            {/*<Footer location={location} history={history}/>*/}
        </Fragment>
    );
};

export default withRouter(Wrapper);