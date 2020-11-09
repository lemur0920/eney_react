import React,{useEffect,useState,useRef} from 'react';
import { Link } from 'react-router-dom';
import patch_call_img from '../../asset/image/patch_call_img.jpg'
import patch_call_img2 from '../../asset/image/patch_call_img2.jpg'
import patch_call_icon1 from '../../asset/image/patch_call_icon1.jpg'
import patch_call_icon2 from '../../asset/image/patch_call_icon2.jpg'
import patch_call_icon3 from '../../asset/image/patch_call_icon3.jpg'
import patch_call_icon4 from '../../asset/image/patch_call_icon4.jpg'
import patch_call_icon5 from '../../asset/image/patch_call_icon5.jpg'
import patch_call_icon6 from '../../asset/image/patch_call_icon6.jpg'
import patch_call_icon7 from '../../asset/image/ga_icon.png'
import patch_call_icon8 from '../../asset/image/dashboard_icon.png'
import btn_audio_play from '../../asset/image/btn_audio_play.gif'
import {withRouter} from 'react-router-dom';
import qs from "qs";
// import SampleAudio from './SampleAudio';

const SampleAudio = {
    education:{
        file:"20150616_764458_322533.mp3",
        title:"교육만만",
        content:"안녕하세요 교육만만 입니다. 최상의 교육 솔루션으로 학생의 미래에 행복을 실현 합니다. 최고의 교육 전문가 기업, 교육만만 입니다."
    },
    shopping:{
        file:"20150430_033549_589817.mp3",
        title:"홍닭",
        content:"전화주셔서 감사합니다. 닭가슴살 종합쇼핑몰 홍닭입니다. 보다 나은 품질과 서비스로 고객만족을 위해 언제나 최선을 다하는 기업이 될 것을 약속드립니다."
    },
    entertainment:{
        file:"20150630_472842_529425.mp3",
        title:"미스터망고투어",
        content:"안녕하세요. 필리핀 앙헬레스, 민도로 투어 전문 1위 투어매니지먼트 미스터망고투어 입니다. 지금 상담원과 연결중 입니다. 잠시만 기다려 주세요."
    },
    media:{
        file:"20130613_660275_347281.mp3",
        title:"오렌지 뮤직",
        content:"음악, 음향에 관한 모든 것, 오렌지 뮤직입니다"
    },
    service:{
        file:"20150518_005438_322435.mp3",
        title:"스키엔아이",
        content:"안녕하세요 피부 속눈썹 전문샵 스킨엔아이입니다. 100%인증제품사용 최고의 품질과 최상의 전문진! 실력이 곧 자존심입니다. 잠시만 기다려주시면 곧 연결해 드리겠습니다"
    },
    government:{
        file:"20130613_838018_933669.mp3",
        title:"영암왕인문화축제",
        content:"기찬 여행！벚꽃세상, 왕인의 영암으로！ 2010 영암왕인문화축제！ 벚꽃 흩날리는 왕인박사유적지에서 벚꽃열차를 타고 왕인과 함께 기찬 여행을 즐겨 보세요. 4월 3일부터 6일까지 영암왕인문화축제에 여러분을 초대합니다."
    },
    corporation:{
        file:"20150508_632870_571715.mp3",
        title:"그룹디티에스",
        content:"안녕하세요? 당신의 든든한 IT 솔루션 파트너, 그룹디티에스 입니다. 저희 그룹디티에스는 최상의 퀄리티를 자부하는 IT 솔루션 컨설팅과, 미국, 일본등지의 상품을 판매하는 서비스를 진행하고 있습니다. 자세한내용은 그룹디티에스 홈페이지를 참조하시거나, 디티에스 전문 상담사에게 문의하시면 친절히 안내를 도와드리겠습니다. 현재 대기자가 많아 연결이 지연되고 있습니다. 잠시만 기다리시면 곧 연결해드리겠습니다. 감사합니다. Your Best partner! Group DTS"
    },
    religion:{
        file:"20130613_403944_598294.mp3",
        title:"템플스테이 봉은사",
        content:"내 마음에 귀를 기울여보세요. 나를 바꾸는 시간, 세상을 바꾸는 지혜가 내 안에 있습니다. 나를 위한 행복 여행. 봉은사 템플스테이"
    },
    startup:{
        file:"20150430_007032_084509.mp3",
        title:"플레이워드 웹",
        content:"안녕하세요? 웹의 모든것, 플레이 위드웹 입니다. 전화주셔서 정말 감사합니다. 잠시만 기다려주세요."
    }
};

const PatchCall = ({location, history}) => {

    const [selectCate, setSelectCate] = useState("education")
    const [selectItem,setSelectItem] = useState(1);

    const audio = useRef()


    useEffect(() => {
        const {target} = qs.parse(location.search,{
            ignoreQueryPrefix:true,
        });

        if(target === "colorring_area"){
            history.push(`${location.pathname}#${target}`)
        }

    },[])

    const toggleTab = (cate) => {
        audio.current.pause();
        audio.current.load();
        setSelectCate(cate)
    }

    const onPlayClick = () => {
        if (audio.current.paused) {
            audio.current.play();
        } else {
            audio.current.pause();
        }
    }
    return (
        <>
        <section className="sub_container">
            <div className="navi clfx">
                <ul className="clfx">
                    <li>Home</li>
                    <li>서비스</li>
                    <li>패치콜</li>
                </ul>
            </div>

            <div className="sub_title_area clfx">
                <div className="img_area"><img src={patch_call_img} alt=""/></div>
                <div className="txt_area">
                    <h1 className="title_style_2">패치콜</h1>
                    <p className="txt_style_2">
                        패치콜은 현재 사용하고 있는 어떤 전화번호(010, 02, 070)에도 붙일 수 있다는 의미이며, 050 가상번호를 착신전환하여 다양한 부가 서비스 및 온라인 상에서
                        인바운드 콜의 유입을 유도하고, 유입별 콜 데이터를 측정, 컬러링 광고 플랫폼을 목적으로 기획되었습니다.
                    </p>
                    <div className="btn_area">
                        <Link to="/price" className="basic-btn01 btn-sky-bg">서비스 이용하기</Link>
                    </div>
                </div>
            </div>

            <div className="use_method">
                <h2 className="title_style_3">활용법</h2>
                <ul className="list_style_1">
                    <li>
                        <h3>1. 신규 O2O 플랫폼 사업자</h3>
                        <p>신규 가맹점 영업을 위해 활용 가맹점별 050가상번호를 적용 착신멘트 또는 TTS엔진을 통해, 가맹점에서 귀사의 O2O플랫폼으로 유입된 전화 임을 컬러링으로
                            알려줌</p>
                    </li>
                    <li>
                        <h3>2. 활성화된 O2O 플랫폼 사업자</h3>
                        <p>
                            프리미엄 서비스로 플랫폼의 충성도UP!가맹점별 유입된 콜 정보를 활용하여 귀사의 플랫폼의 광고정책을 만들어보세요.
                            <span className="f-bsky">예) 배달의 민족은 콜데이터 기반의 프리미엄 서비스 사용있습니다. </span>
                        </p>
                    </li>
                    <li>
                        <h3>3. 소셜 플랫폼을 통한 매출증대</h3>
                        <p>콘텐츠와 콜과의 상관관계를 확인 수집된 정보는 신규 마케팅을 위한 잠재고객을 추정하여, 소셜 플랫폼을 홀용한 홍보 전략을 수립할 수 있습니다. </p>
                    </li>
                </ul>
            </div>
            <div className="production_process">
                <div className="inner clfx">
                    <div className="left_txt">
                        <h2>패치콜 소개</h2>
                        <p>
                            모바일 전화문의 수집을 통한 마케팅<br/> 인사이트 확보
                        </p>
                    </div>
                    <div className="video_area">
                        <iframe src="https://www.youtube.com/embed/06Ys0AjKKyg?controls=0" frameBorder="0"
                                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                                allowFullScreen></iframe>
                    </div>
                </div>
            </div>
            <div className="production_case">
                <h2>추천제작사례</h2>
                <div className="tab_style_1">
                    <ul className="clfx">
                        <li className={selectItem === 1 && "on"}>
                            <button  onClick={() => setSelectItem(1)}>O2O플랫폼</button>
                        </li>
                        <li className={selectItem === 2 && "on"}>
                            <button  onClick={() => setSelectItem(2)}>홈페이지</button>
                        </li>
                    </ul>
                </div>
                <ul className="production_case_list clfx" hidden={selectItem !== 1}>
                    <li>
                        <a href="#"><img src={require("../../asset/image/service_introduce/o2o_img_01.jpg")}alt=""/></a>
                        <h3>운전면허플러스(전화유입분석) : 학원앱(운전면허플러스)</h3>
                        <p>"운전면허플러스" 운전면허 필기시험 준비 어플로 450만 사용자를 확보하고 있습니다. 운전면허 카테고리 분류 1등 앱으로 에네이 패치콜을 6년간 사용하고 있습니다.</p>
                    </li>
                    <li>
                        <a href="#"><img src={require("../../asset/image/service_introduce/o2o_img_02.png")} alt="" style={{top:"20%",height:"60%"}}/></a>
                        <h3>마피아컴퍼니(전화유입분석)</h3>
                        <p>글로벌 음악 플랫폼 회사로 180개 국에서 온 250만 명의 사용자들로 유명한 기악곡 플랫폼 업체이며 유망받는 스타트업체이다. 플랫폼의 전화유입분석에 사용하고 있습니다.</p>
                    </li>
                    <li>
                        <a href="#"><img src={require("../../asset/image/service_introduce/o2o_img_03.jpg")} alt=""/></a>
                        <h3>쇼빅(전화유입검증)</h3>
                        <p>청주,천안, 아산, 세종 특화된 지역쿠폰 어플리케이션 플랫폼입니다.</p>
                    </li>
                </ul>
                <ul className="production_case_list clfx" hidden={selectItem !== 2}>
                    <li>
                        <a href="#"><img src={require("../../asset/image/service_introduce/homepage_img_01.jpg")}alt=""/></a>
                        <h3>아카뷰(전화유입분석)</h3>
                        <p>해외고 전용 교육플랫폼으로 A to Z 전적으로 에네이 클라우드 기반으로 구축되었습니다.  신규 사업을 하고자 한다면 합리적인 비용으로 당신의 플랫폼을 구축 할 수 있습니다.</p>
                    </li>
                    <li>
                        <a href="#"><img src={require("../../asset/image/service_introduce/homepage_img_02.png")} alt=""/></a>
                        <h3>서울시 캠퍼스타운(전화유입분석)</h3>
                        <p>서울시 캠퍼스타운은 서울시에서 운영하는 지역, 대학, 서울시가 함께하는 스타트업 엑셀러레이팅 플랫폼으로 패치콜를 활요한 전화상담에 대한 서비스를 이용중에 있습니다.</p>
                    </li>
                    <li>
                        <a href="#"><img src={require("../../asset/image/service_introduce/homepage_img_03.png")} alt=""/></a>
                        <h3>숭실대학교 창업지원단 (안심전호, 전화유입분석)</h3>
                        <p>서울시에 위치한 종합대학으로  창업지원단에서 제공되는 멘토링 진행시 안심번호 및 플랫폼 유입분석에 활용되고 있습니다. </p>
                    </li>
                </ul>
            </div>

            <div className="function_list">
                <h2 className="title_style_3">주요기능</h2>
                <ul className="list_style_2 clfx">
                    <li>
                        <div className="icon"><img src={patch_call_icon7} alt=""/></div>
                        <div className="txt_area">
                            <h3>Googla Analytics 연동하기</h3>
                            <p>
                                홈페이지에 가상번호를 삽입하고 패치콜과<br/>
                                Google Analytics를 연동하면 키워드 채널를 분석 할수 있음
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=3&id=349">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon8} alt=""/></div>
                        <div className="txt_area">
                            <h3>대시보드</h3>
                            <p>
                                O2O/광고대행/콜분석 정형 대시보드 제공을 통해<br/>
                                 인바운드 콜에대한 인사이트를 활보 할 수 있음
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&page=3&id=349">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon1} alt=""/></div>
                        <div className="txt_area">
                            <h3>컬러링 / 착신멘트 / TTS엔진</h3>
                            <p>
                                컬러링 : 귀사의 컬러링을 모든 가상번호에 적용 사용자의 충성도 증대<br/>
                                착신멘트 : 콜 유입시 귀사의 가맹점에 짧은 음원을 들려줌<br/>
                                TTS엔진: 번호별 DB에 등록된 발신지명을 기계음으로 들려줌
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=390">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon2} alt=""/></div>
                        <div className="txt_area">
                            <h3>개발자센터 (API)</h3>
                            <p>
                                API를 활용하여 귀사의 플랫폼 DB연동 시 편의 제공(PHP, JSP, C#), <br/>
                                음성부가서비스(컬러링, 착신멘트, TTS엔진)등을 실시간 변경
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=358">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon3} alt=""/></div>
                        <div className="txt_area">
                            <h3>클릭투콜 버튼</h3>
                            <p>
                                가상번호 1개로 온라인 홍보시 채널별 유입콜의 양을 추적 <br/>
                                광고채널별 홍보전에 클릭투콜 버튼을 발행하여 홍보시 <br/>
                                Click to Call 버튼 삽입
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=391">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon4} alt=""/></div>
                        <div className="txt_area">
                            <h3>녹취</h3>
                            <p>
                                신청한 번호 별 전수녹취 <br/>
                                녹취파일은 웹 콜로그 조회에서 확인 가능
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=348">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon5} alt=""/></div>
                        <div className="txt_area">
                            <h3>CALL BACK 메세징</h3>
                            <p>
                                전화 부재중 또는 통화종료 후 발신자에게 이벤트, 홍보관련 메시지 전송 <br/>
                                SMS / LMS / MMS 모두 확인 가능
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=350">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div className="icon"><img src={patch_call_icon6} alt=""/></div>
                        <div className="txt_area">
                            <h3>콜로그</h3>
                            <p>
                                기본적인 콜로그정보 확인(엑셀 다운로드 및 API 제공) <br/>
                                캠페인별 관리를 통한 콜로그 확인은 패치콜 BI 서비스에서 확인
                            </p>
                            <div className="btn_area">
                                <Link to="/customerservice?type=help&id=352">→ 자세히 알아보기</Link>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

            <div className="colorring_area" id="colorring_area">
                <div className="intro clfx">
                    <div className="img_area">
                        <img src={patch_call_img2} alt=""/>
                    </div>
                    <div className="txt_area">
                        <p>
                            컬러링을 온라인에서 바로 신청해보세요. <br/>
                            전문 성우가 녹음하는 컬러링을 가상번호, 전국대표번호에 <br/>
                            컬러링과 착신 멘트로 적용할 수 있습니다.
                            <span>- 제작기간 : 일주일, 제작비용 : 30,000원(VAT별도)</span>
                        </p>
                        <div className="btn_area">
                            <Link to="/coloring_apply" className="basic-btn01 btn-sky-bg">컬러링 제작 의뢰하기</Link>
                        </div>
                    </div>
                </div>

                <div className="colouring_sample">
                    <h1>컬러링 샘플듣기</h1>
                    <div className="colouring_sample_btn">
                        <ul>
                            <li className={selectCate === "education" ? "on" : ""} onClick={() => toggleTab("education")}>
                                <button>교육/언론</button>
                            </li>
                            <li className={selectCate === "shopping" ? "on" :""} onClick={() => toggleTab("shopping")}>
                                <button>쇼핑/외식</button>
                            </li>
                            <li className={selectCate === "entertainment" ? "on" : ""} onClick={() => toggleTab("entertainment")}>
                                <button>오락/레져</button>
                            </li>
                            <li className={selectCate === "media" ? "on" : ""} onClick={() => toggleTab("media")}>
                                <button>미디어/문화</button>
                            </li>
                            <li className={selectCate === "service" ? "on" :""} onClick={() => toggleTab("service")}>
                                <button>서비스/의료</button>
                            </li>
                            <li className={selectCate === "government" ? "on" :""} onClick={() => toggleTab("government")}>
                                <button>관공서/법률</button>
                            </li>
                            <li className={selectCate === "corporation" ? "on" :""} onClick={() => toggleTab("corporation")}>
                                <button>일반기업/금융</button>
                            </li>
                            <li className={selectCate === "religion" ? "on" :""} onClick={() => toggleTab("religion")}>
                                <button>종교/기타</button>
                            </li>
                            <li className={selectCate === "startup" ? "on" :""} onClick={(e) => {toggleTab("startup")}}>
                                <button>1인창조 기업</button>
                            </li>
                        </ul>
                    </div>

                    <div className="colouring_sample_cont">
                        <div>
                            <div className="audio_area">
                                    <audio controls ref={audio}>
                                        <source src={require(`../../asset/audio/${SampleAudio[selectCate].file}`)} type="audio/mp3" />
                                        {/*<source src="horse.mp3" type="audio/mpeg"/>*/}
                                    </audio>
                                <div className="btn_play">
                                    <button onClick={(e) => {onPlayClick()}}><img src={btn_audio_play} alt="재생"/></button>
                                </div>
                                <h2>{SampleAudio[selectCate].title}</h2>
                                <p>
                                    {SampleAudio[selectCate].content}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </>
    );
};

export default withRouter(PatchCall);