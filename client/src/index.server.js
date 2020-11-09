import React from 'react';
import ReactDOMServer from 'react-dom/server';
import express from 'express';
import compression from 'compression'
import cors from "cors";
import { StaticRouter,Router } from 'react-router-dom';
import App from './App';
import path from 'path';
import { createStore, applyMiddleware } from 'redux';
import { ServerStyleSheets, ThemeProvider } from '@material-ui/core/styles';
import { Provider } from 'react-redux';
import Themes from "./themes";
import CssBaseline from '@material-ui/core/CssBaseline';
import createSagaMiddleware from 'redux-saga';
import rootReducer, { rootSaga } from './modules';
import PreloadContext from './lib/PreloaderContext';
import { END } from 'redux-saga';

import { ChunkExtractor, ChunkExtractorManager } from '@loadable/server';

// const manifest = JSON.parse(
//     fs.readFileSync(path.resolve('./build/asset-manifest.json'), 'utf8'),
// );
function isRunningOnServerSide() {
    return typeof(window) === "undefined";
};

const statsFile = path.resolve('./build/loadable-stats.json');

function createPage(root, tags, css) {
    return `<!DOCTYPE html>
  <html lang="ko">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width,initial-scale=1,shrink-to-fit=no"
    />
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <meta name="theme-color" content="#000000" />
    <title>ENEY</title>
    ${tags.styles}
    ${tags.links}
    ${tags.scripts}
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.7.js"></script>

    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-153520125-2"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());
    
      gtag('config', 'UA-153520125-2');
    </script>

    <script>
        Date.prototype.yyyymmdd = function() {
            const mm = this.getMonth() + 1;
            const dd = this.getDate();

            return [this.getFullYear(),
                (mm>9 ? '' : '0') + mm,
                (dd>9 ? '' : '0') + dd
            ].join('');
        };

        Date.prototype.hhmmss = function() {
            const hh = this.getHours();
            const mm = this.getMinutes();
            const ss = this.getSeconds();

            return [(hh>9 ? '' : '0') + hh,
                (mm>9 ? '' : '0') + mm,
                (ss>9 ? '' : '0') + ss,
            ].join('');
        };


        Date.prototype.yyyymmddhhmmss = function() {
            return this.yyyymmdd() + this.hhmmss();
        };
    </script>
    <style id="jss-server-side">${css}</style>
  </head>
  <body>
    <noscript>You need to enable JavaScript to run this app.</noscript>
    <div id="root">
      ${root}
    </div>
  </body>
  </html>
    `;
}

const app = express();

// 서버사이드 렌더링을 처리 할 핸들러 함수입니다.
const serverRender = async (req, res, next) => {
    // 이 함수는 404가 떠야 하는 상황에 404를 띄우지 않고 서버사이드 렌더링을 해줍니다.

    const context = {};
    // const history = createBrowserHistory();
    const sagaMiddleware = createSagaMiddleware();

    const store = createStore(
        rootReducer,
        applyMiddleware(sagaMiddleware)
    );

    const sagaPromise = sagaMiddleware.run(rootSaga).toPromise();

    const preloadContext = {
        done: false,
        promises: []
    };

    // 필요한 파일 추출하기 위한 ChunkExtractor
    const extractor = new ChunkExtractor({ statsFile });
    const sheets = new ServerStyleSheets();

    const jsx = (
        <ChunkExtractorManager extractor={extractor}>
            <PreloadContext.Provider value={preloadContext}>
                {/*<Router history={customHistory}>*/}
                <Provider store={store}>
                    <StaticRouter location={req.url} context={context}>
                        {/*sheets.collect(*/}
                            <ThemeProvider theme={Themes.default}>
                                <CssBaseline />
                                <App />
                            </ThemeProvider>
                        {/*),*/}
                    </StaticRouter>
                </Provider>
                {/*</Router>*/}
            </PreloadContext.Provider>
        </ChunkExtractorManager>
    );

    const css = sheets.toString();

    ReactDOMServer.renderToStaticMarkup(jsx); // renderToStaticMarkup 으로 한번 렌더링합니다.
    // store.close();
    store.dispatch(END); // redux-saga 의 END 액션을 발생시키면 액션을 모니터링하는 saga 들이 모두 종료됩니다.
    try {
        await sagaPromise; // 기존에 진행중이던 saga 들이 모두 끝날때까지 기다립니다.
        // await Promise.all(preloadContext.promises); // 모든 프로미스를 기다립니다.
    } catch (e) {
        return res.status(500);
    }
    preloadContext.done = true;
    const root = ReactDOMServer.renderToString(jsx); // 렌더링을 합니다.
    // JSON 을 문자열로 변환하고 악성스크립트가 실행되는것을 방지하기 위해서 < 를 치환처리
    // https://redux.js.org/recipes/server-rendering#security-considerations
    const stateString = JSON.stringify(store.getState()).replace(/</g, '\\u003c');
    const stateScript = `<script>__PRELOADED_STATE__ = ${stateString}</script>`; // 리덕스 초기 상태를 스크립트로 주입합니다.

    // 미리 불러와야 하는 스타일 / 스크립트를 추출하고
    const tags = {
        scripts: stateScript + extractor.getScriptTags(), // 스크립트 앞부분에 리덕스 상태 넣기
        links: extractor.getLinkTags(),
        styles: extractor.getStyleTags()
    };


    res.send(createPage(root, tags,css)); // 결과물을 응답합니다.
};

const serve = express.static(path.resolve('./build'), {
    index: false,
});

app.use(compression())
app.use(cors());
app.use(serve);
app.use(serverRender);

app.listen(9981, () => {
    console.log('running on http://localhost:9981');
});
