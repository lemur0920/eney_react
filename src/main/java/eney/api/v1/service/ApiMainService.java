package eney.api.v1.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eney.api.v1.domain.ApiCommonVo;
import eney.api.v1.domain.ApiLogVo;
import eney.api.v1.domain.ApiPaginationListVo;
import eney.api.v1.domain.ApiTokenVo;
import eney.api.v1.mapper.ApiMainDao;
import eney.domain.*;
import eney.mapper.IvrDao;
import eney.util.IPUtils;
import eney.util.RandomString;
import eney.util.UserAuthUtil;
import eney.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eney.api.v1.domain.ApiCommonVo;
import eney.api.v1.domain.ApiLogVo;
import eney.api.v1.domain.ApiPaginationListVo;
import eney.api.v1.domain.ApiTokenVo;
import eney.api.v1.mapper.ApiMainDao;
import eney.domain.AgentVO;
import eney.domain.CallLogVO;
import eney.domain.ColoringUploadVO;
import eney.domain.MessageVO;
import eney.domain.ServiceMessagingVO;
import eney.domain.UserVO;
import eney.exception.InvalidDataException;
import eney.property.EneyProperties;
import eney.service.IvrService;
import eney.service.MessageService;
import eney.service.SupplyService;
import eney.service.UserService;
import eney.util.IPUtils;
import eney.util.RandomString;
import eney.util.UserAuthUtil;
import eney.util.ValidationUtil;

@Service
public class ApiMainService {

    private static final Logger logger = LoggerFactory.getLogger(ApiMainService.class);

    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;
    @Resource
    SupplyService supplyService;
    @Resource
    IvrService ivrService;
    @Resource
    IvrDao ivrDao;

    @Resource
    MessageService messageService;
    @Resource
    ApiMainDao apiMainDao;

    @Resource
    ValidationUtil validationUtil;
    @Resource
    IPUtils ipUtil;

    @Resource
    private EneyProperties eneyProperties;

    /** 토큰 부분 **/
    /**
     * API 토큰키 생성 (만료일 없음)
     *
     * @param userInfo 토큰을 생성하는 사용자 정보
     * @return 생성된 토큰정보
     */
    public ApiTokenVo generateToken(UserVO userInfo) {
        return generateToken(null, userInfo);
    }

    /**
     * API 토큰키 생성
     *
     * @param token_issued_date 만료일 (만료일을 지정하지 않으면 null)
     * @param userInfo          토큰을 생성하는 사용자 정보
     * @return 생성된 토큰 정보
     */
    public ApiTokenVo generateToken(Date token_issued_date, UserVO userInfo) {
        ApiTokenVo genTokenInfo = new ApiTokenVo();
        RandomString randomString = new RandomString(32);

        //TODO 토큰 생성 권한 확인 필요 (생성 권한, 토큰 최대 생성 개수 확인 등)
        int loopCount = 0;
        do {
            /* random key 생성 및 중복 검사 부분 */
            String randomTokenKey = randomString.nextString();
            ApiTokenVo selectQuery = new ApiTokenVo();
            selectQuery.setToken_key(randomTokenKey);

            ApiTokenVo selectToken = apiMainDao.selectTokenInfo(selectQuery);

            if (selectToken == null) {
                /* 중복되지 않는 경우 */
                genTokenInfo.setToken_key(randomTokenKey);
                break;
            }

            if (loopCount >= eneyProperties.getPortal().getApiTokenLen()) {
                logger.error("[API v1 토큰 생성]실패 - 랜덤으로 생성된 값이 30회 이상 중복되었습니다. 랜덤 알고리즘 또는 랜덤 문자열 길이값을 확인해 주세요("
                        + "마지막 생성 키: " + randomTokenKey
                        + ", userInfo: " + userInfo
                        + ", ip: " + request.getRemoteAddr() + ")");
                throw new RuntimeException("토큰 생성중 오류가 발생하였습니다. \n관리자에게 문의해 주십시오.");
            }
        } while (true);
        genTokenInfo.setToken_userid(userInfo.getUserid());
        genTokenInfo.setToken_issued_date(token_issued_date);

        /* 생성된 토큰을 DB에 넣음 */
        apiMainDao.insertTokenInfo(genTokenInfo);

        logger.info("[API v1 토큰 생성]성공 ("
                + "tokenInfo: " + genTokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        return genTokenInfo;
    }

    public ApiTokenVo generateTokenWithIp(ApiTokenVo apiTokenVo, UserVO userInfo) {
        ApiTokenVo genTokenInfo = new ApiTokenVo();
        RandomString randomString = new RandomString(32);

        //TODO 토큰 생성 권한 확인 필요 (생성 권한, 토큰 최대 생성 개수 확인 등)
        int loopCount = 0;
        do {
            /* random key 생성 및 중복 검사 부분 */
            String randomTokenKey = randomString.nextString();
            ApiTokenVo selectQuery = new ApiTokenVo();
            selectQuery.setToken_key(randomTokenKey);

            ApiTokenVo selectToken = apiMainDao.selectTokenInfo(selectQuery);

            if (selectToken == null) {
                /* 중복되지 않는 경우 */
                genTokenInfo.setToken_key(randomTokenKey);
                break;
            }

            if (loopCount >= eneyProperties.getPortal().getApiTokenLen()) {
                logger.error("[API v1 토큰 생성]실패 - 랜덤으로 생성된 값이 30회 이상 중복되었습니다. 랜덤 알고리즘 또는 랜덤 문자열 길이값을 확인해 주세요("
                        + "마지막 생성 키: " + randomTokenKey
                        + ", userInfo: " + userInfo
                        + ", ip: " + request.getRemoteAddr() + ")");
                throw new RuntimeException("토큰 생성중 오류가 발생하였습니다. \n관리자에게 문의해 주십시오.");
            }
        } while (true);
        genTokenInfo.setToken_userid(userInfo.getUserid());
        genTokenInfo.setToken_issued_date(null);
        genTokenInfo.setService(apiTokenVo.getService());
        genTokenInfo.setAllocation_ip(apiTokenVo.getAllocation_ip());

        /* 생성된 토큰을 DB에 넣음 */
        apiMainDao.insertTokenInfo(genTokenInfo);

        logger.info("[API v1 토큰 생성]성공 ("
                + "tokenInfo: " + genTokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        return genTokenInfo;
    }



    public ApiTokenVo generateMessageToken(UserVO userInfo) {
        ApiTokenVo genTokenInfo = new ApiTokenVo();
        RandomString randomString = new RandomString(32);

        //TODO 토큰 생성 권한 확인 필요 (생성 권한, 토큰 최대 생성 개수 확인 등)
        int loopCount = 0;
        do {
            /* random key 생성 및 중복 검사 부분 */
            String randomTokenKey = randomString.nextString();
            ApiTokenVo selectQuery = new ApiTokenVo();
            selectQuery.setToken_key(randomTokenKey);

            ApiTokenVo selectToken = apiMainDao.selectMessageTokenInfo(selectQuery);

            if (selectToken == null) {
                /* 중복되지 않는 경우 */
                genTokenInfo.setToken_key(randomTokenKey);
                break;
            }

            if (loopCount >= eneyProperties.getPortal().getApiTokenLen()) {
                logger.error("[API v1 토큰 생성]실패 - 랜덤으로 생성된 값이 30회 이상 중복되었습니다. 랜덤 알고리즘 또는 랜덤 문자열 길이값을 확인해 주세요("
                        + "마지막 생성 키: " + randomTokenKey
                        + ", userInfo: " + userInfo
                        + ", ip: " + request.getRemoteAddr() + ")");
                throw new RuntimeException("토큰 생성중 오류가 발생하였습니다. \n관리자에게 문의해 주십시오.");
            }
        } while (true);
        genTokenInfo.setToken_userid(userInfo.getUserid());

        /* 생성된 토큰을 DB에 넣음 */
        apiMainDao.insertMessageTokenInfo(genTokenInfo);

        logger.info("[API v1 토큰 생성]성공 ("
                + "tokenInfo: " + genTokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        return genTokenInfo;
    }

    public ApiTokenVo generateMessageTokenWithIp(ApiTokenVo apiTokenVo,UserVO userInfo) {
        ApiTokenVo genTokenInfo = new ApiTokenVo();
        RandomString randomString = new RandomString(32);

        //TODO 토큰 생성 권한 확인 필요 (생성 권한, 토큰 최대 생성 개수 확인 등)
        int loopCount = 0;
        do {
            /* random key 생성 및 중복 검사 부분 */
            String randomTokenKey = randomString.nextString();
            ApiTokenVo selectQuery = new ApiTokenVo();
            selectQuery.setToken_key(randomTokenKey);

            ApiTokenVo selectToken = apiMainDao.selectMessageTokenInfo(selectQuery);

            if (selectToken == null) {
                /* 중복되지 않는 경우 */
                genTokenInfo.setToken_key(randomTokenKey);
                break;
            }

            if (loopCount >= eneyProperties.getPortal().getApiTokenLen()) {
                logger.error("[API v1 토큰 생성]실패 - 랜덤으로 생성된 값이 30회 이상 중복되었습니다. 랜덤 알고리즘 또는 랜덤 문자열 길이값을 확인해 주세요("
                        + "마지막 생성 키: " + randomTokenKey
                        + ", userInfo: " + userInfo
                        + ", ip: " + request.getRemoteAddr() + ")");
                throw new RuntimeException("토큰 생성중 오류가 발생하였습니다. \n관리자에게 문의해 주십시오.");
            }
        } while (true);
        genTokenInfo.setToken_userid(userInfo.getUserid());
        genTokenInfo.setService(apiTokenVo.getService());
        genTokenInfo.setAllocation_ip(apiTokenVo.getAllocation_ip());

        /* 생성된 토큰을 DB에 넣음 */
        apiMainDao.insertMessageTokenInfo(genTokenInfo);

        logger.info("[API v1 토큰 생성]성공 ("
                + "tokenInfo: " + genTokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        return genTokenInfo;
    }


    /**
     * 사용자가 발급받은 토큰 리스트 출력 (seq 포함)
     *
     * @param userInfo 사용자 정보
     * @return 발급받은 토큰  리스트
     */
    public List<ApiTokenVo> getTokenInfoList(UserVO userInfo) {
        ApiTokenVo selectQuery = new ApiTokenVo();
        selectQuery.setToken_userid(userInfo.getUserid());

        return apiMainDao.selectTokenInfoList(selectQuery);
    }

    public List<ApiTokenVo> getMessageTokenInfoList(UserVO userInfo) {
        ApiTokenVo selectQuery = new ApiTokenVo();
        selectQuery.setToken_userid(userInfo.getUserid());

        return apiMainDao.selectMessageTokenInfoList(selectQuery);
    }

    public List<ApiTokenVo> getMessageTokenInfoListByVO(ApiTokenVo apiTokenVo) {

        return apiMainDao.selectMessageTokenInfoList(apiTokenVo);
    }

    public Map<String, Object> selectMessageTokenInfoList(ApiTokenVo apiTokenVo) {

        apiTokenVo.setPageSize(5);
        apiTokenVo.setTotalCount(apiMainDao.selectTokenAllInfoListCnt(apiTokenVo));

        Map<String, Object> map = new HashMap<>();

        List<ApiTokenVo> list = apiMainDao.selectTokenAllInfoList(apiTokenVo);

        map.put("tokenList", list);
        map.put("page", apiTokenVo);

        return map;
    }

    /**
     * 사용자가 발급받은 토큰 리스트 출력(사용자에게 공개되지 않아야 될 항목들을 제외)
     *
     * @param userInfo 사용자 정보
     * @return 발급받은 토큰 리스트 (사용자에게 공개되지 않아야 될 항목들이 제외됨)
     */
    public List<ApiTokenVo> getUserTokenProtectInfoList(UserVO userInfo) {
        List<ApiTokenVo> tokenInfoList = getTokenInfoList(userInfo);
        if (tokenInfoList == null) {
            return null;
        }

        for (ApiTokenVo eachTokenInfo : tokenInfoList) {
            eachTokenInfo.setToken_idx(null);
        }
        return tokenInfoList;
    }

    /**
     * 토큰 정보 수정
     *
     * @param editQuery 수정할 토큰 정보
     * @param userInfo  사용자 정보
     * @return 수정 여부
     */
    @Transactional
    public boolean editTokenInfo(ApiTokenVo editQuery, UserVO userInfo) {
        ApiTokenVo tokenInfo = apiMainDao.selectTokenInfo(editQuery);
        if (tokenInfo == null
                || !(userInfo.getUserid().equals(tokenInfo.getToken_userid())
                || UserAuthUtil.isAuthCheck("AUTH_ADMIN", userInfo))) {
            /* 토큰 정보가 없거나 수정 권한이 없는 경우 */
            return false;
        }
        editQuery.setToken_idx(tokenInfo.getToken_idx());

        if (apiMainDao.updateTokenInfo(editQuery) != 1) {
            logger.error("[API v1 토큰 수정] 실패 - 1개 이외의 행이 DB에서 변경되었습니다. ("
                    + "tokenInfo: " + tokenInfo
                    + ", editQuery: " + editQuery
                    + ", userInfo: " + userInfo
                    + ", ip: " + request.getRemoteAddr() + ")");
            throw new RuntimeException("[API v1 토큰 수정] 실패 - 1개 이외의 행이 DB에서 변경되었습니다.");
        }

        logger.info("[API v1 토큰 수정]성공 ("
                + "editQuery: " + editQuery
                + ", 변경 전 tokenInfo: " + tokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        return true;
    }

    /**
     * 토큰을 이용하여 사용자 계정 정보를 읽어옴
     *
     * @param tokenKey 토큰값
     * @return 사용자 정보 (userVO)
     */
    public UserVO getUserInfoForToken(String tokenKey) {
        ApiTokenVo selectTokenQuery = new ApiTokenVo();
        selectTokenQuery.setToken_key(tokenKey);
        selectTokenQuery.setToken_enable_yn(true);

        ApiTokenVo tokenInfo = apiMainDao.selectTokenInfo(selectTokenQuery);
        if (tokenInfo == null) {
            logger.debug("[API v1 사용자 정보 조회] 실패 - 입력받은  토큰값을 찾을 수 없습니다. ("
                    + "tokenKey: " + tokenKey
                    + ", ip: " + request.getRemoteAddr() + ")");
            return null;
        }

        UserVO userInfo = userService.loadUserByUsername(tokenInfo.getToken_userid());
        if (userInfo == null) {
            logger.debug("[API v1 사용자 정보 조회] 실패 - 사용자 계정을 찾을 수 없습니다 ("
                    + "tokenKey: " + tokenKey
                    + "tokenInfo: " + tokenInfo
                    + ", ip: " + request.getRemoteAddr() + ")");
            return null;
        }

        logger.debug("[API v1 사용자 정보 조회] 성공 ("
                + "tokenInfo: " + tokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        //TODO 권한도 넣어주도록 수정해야 됨
        return userInfo;
    }

    public UserVO getUserInfoForMessageToken(String tokenKey) {
        ApiTokenVo selectTokenQuery = new ApiTokenVo();
        selectTokenQuery.setToken_key(tokenKey);
        selectTokenQuery.setToken_enable_yn(true);

        ApiTokenVo tokenInfo = apiMainDao.selectMessageTokenInfo(selectTokenQuery);
        if (tokenInfo == null) {
            logger.debug("[API v1 사용자 정보 조회] 실패 - 입력받은  토큰값을 찾을 수 없습니다. ("
                    + "tokenKey: " + tokenKey
                    + ", ip: " + request.getRemoteAddr() + ")");
            return null;
        }

        UserVO userInfo = userService.loadUserByUsername(tokenInfo.getToken_userid());
        if (userInfo == null) {
            logger.debug("[API v1 사용자 정보 조회] 실패 - 사용자 계정을 찾을 수 없습니다 ("
                    + "tokenKey: " + tokenKey
                    + "tokenInfo: " + tokenInfo
                    + ", ip: " + request.getRemoteAddr() + ")");
            return null;
        }

        logger.debug("[API v1 사용자 정보 조회] 성공 ("
                + "tokenInfo: " + tokenInfo
                + ", userInfo: " + userInfo
                + ", ip: " + request.getRemoteAddr() + ")");
        //TODO 권한도 넣어주도록 수정해야 됨
        return userInfo;
    }


    public ServiceMessagingVO getUserIpForUserVO(UserVO user) {

        return userService.getUserIpForUserVO(user);
    }

    /** 050 번호 자원 부분 **/

    /**
     * 050 번호 정보 수정
     *
     * @param userId        사용자 ID
     * @param tokenKey      API 토큰키
     * @param updateVnoInfo 수정할 정보
     * @return 처리 결과(처리여부, 수정한 번호자원 정보 등)
     */
    public ApiCommonVo<AgentVO> updateVno(String userId, String tokenKey, AgentVO updateVnoInfo) {

        long startTime = System.nanoTime();
        UserVO userVO = getUserInfoForToken(tokenKey);
        if (userVO == null) {
            String log = "[API v1 050 정보 수정] 실패 - 토큰값이 유효하지 않습니다 ("
                    + "tokenKey: " + tokenKey + ", userId: " + userId
                    + ", updateVnoInfo: " + updateVnoInfo + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_VNO, HttpServletResponse.SC_UNAUTHORIZED, tokenKey, null, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<AgentVO>(HttpServletResponse.SC_UNAUTHORIZED, "050", "토큰값이 유효하지 않습니다", null);
        }
        updateVnoInfo.setUser_id(userVO.getUserid());
        AgentVO oldAgentVO = supplyService.getAgentVO(updateVnoInfo);

        //1.번호 권한 검사 포함
        if (oldAgentVO == null || !oldAgentVO.getUser_id().equals(userId)) {
            logger.info("[API v1 050 정보 수정] 실패 - 해당 번호를 수정할 권한이 없습니다 ("
                    + "tokenKey: " + tokenKey
                    + ", userId: " + userId
                    + ", updateVnoInfo: " + updateVnoInfo
                    + ", userInfo: " + userVO
                    + ", ip: " + request.getRemoteAddr() + ")");

            String log = "[API v1 050 정보 수정] 실패 - 해당 번호를 수정할 권한이 없습니다 ("
                    + "tokenKey: " + tokenKey + ", userId: " + userId
                    + ", updateVnoInfo: " + updateVnoInfo + ", userInfo: " + userVO
                    + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_VNO, HttpServletResponse.SC_FORBIDDEN, tokenKey, userVO, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

            return new ApiCommonVo<AgentVO>(HttpServletResponse.SC_FORBIDDEN, "050", "해당 번호를 수정할 권한이 없습니다.", null);
        }

        updateVnoInfo.setIdx(oldAgentVO.getIdx());

        //2.데이터 타입 유효 검사
        try {
            validationUtil.validate(updateVnoInfo);
        } catch (InvalidDataException e) {
            String log = "[API v1 050 정보 수정] 실패 -데이터 타입이 유효하지 않습니다.("
                    + "exceptionMessage: " + e.getMessage() + ", tokenKey: " + tokenKey
                    + ", userId: " + userId + ", updateVnoInfo: " + updateVnoInfo
                    + ", userInfo: " + userVO + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_VNO, HttpServletResponse.SC_BAD_REQUEST, tokenKey, userVO, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<AgentVO>(HttpServletResponse.SC_BAD_REQUEST, "050", "데이터 타입이 유효하지 않습니다", null);
        }

        //3.컬러링 권한 검사
        if (updateVnoInfo.getColorring_idx() != null) {
            if (updateVnoInfo.getColorring_idx() > 0) {
                ColoringUploadVO colorringVo = new ColoringUploadVO();
                colorringVo.setUserid(userVO.getUserid());
                colorringVo.setIdx(updateVnoInfo.getColorring_idx());
                ColoringUploadVO selectVo = supplyService.getColoringUploadVO(colorringVo);
                //권한없음
                if (selectVo == null) {
                    logger.info("[API v1 050 정보 수정] 실패 - 해당 컬러링을 사용할 권한이 없습니다 ("
                            + "tokenKey: " + tokenKey
                            + ", userId: " + userId
                            + ", updateVnoInfo: " + updateVnoInfo
                            + ", userInfo: " + userVO
                            + ", ip: " + request.getRemoteAddr() + ")");

                    String log = "[API v1 050 정보 수정] 실패 - 해당 컬러링을 사용할 권한이 없습니다 ("
                            + "tokenKey: " + tokenKey + ", userId: " + userId
                            + ", updateVnoInfo: " + updateVnoInfo + ", userInfo: " + userVO
                            + ", ip: " + request.getRemoteAddr() + ")";
                    logger.info(log);
                    apiLogger(ApiLogVo.CATE_VNO, HttpServletResponse.SC_FORBIDDEN, tokenKey, userVO, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

                    return new ApiCommonVo<AgentVO>(HttpServletResponse.SC_FORBIDDEN, "050", "해당 컬러링을 사용할 권한이 없습니다.", null);
                }
            }
        }

        //4.착신멘트 권한 검사
        if (updateVnoInfo.getRcvment_idx() != null)
            if (updateVnoInfo.getRcvment_idx() > 0) {
                ColoringUploadVO colorringVo = new ColoringUploadVO();
                colorringVo.setUserid(userVO.getUserid());
                colorringVo.setIdx(updateVnoInfo.getRcvment_idx());
                ColoringUploadVO selectVo = supplyService.getRcvmentUploadVO(colorringVo);
                //권한없음
                if (selectVo == null) {
                    logger.info("[API v1 050 정보 수정] 실패 - 해당 착신멘트를 수정할 권한이 없습니다 ("
                            + "tokenKey: " + tokenKey
                            + ", userId: " + userId
                            + ", updateVnoInfo: " + updateVnoInfo
                            + ", userInfo: " + userVO
                            + ", ip: " + request.getRemoteAddr() + ")");

                    String log = "[API v1 050 정보 수정] 실패 - 해당 착신멘트를 수정할 권한이 없습니다 ("
                            + "tokenKey: " + tokenKey + ", userId: " + userId
                            + ", updateVnoInfo: " + updateVnoInfo + ", userInfo: " + userVO
                            + ", ip: " + request.getRemoteAddr() + ")";
                    logger.info(log);
                    apiLogger(ApiLogVo.CATE_VNO, HttpServletResponse.SC_FORBIDDEN, tokenKey, userVO, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

                    return new ApiCommonVo<AgentVO>(HttpServletResponse.SC_FORBIDDEN, "050", "해당 착신멘트를 사용할 권한이 없습니다.", null);
                }
            }


        //실제 업데이트 호출
        supplyService.update050Agent(updateVnoInfo);

        AgentVO updatedAgentVO = supplyService.getAgentVO(updateVnoInfo);

        long elapsedTime = System.nanoTime() - startTime;

        String log = "[API v1 050 정보 수정] 성공("
                + "처리시간: " + elapsedTime + ", tokenKey: " + tokenKey
                + ", userId: " + userId + ", updateVnoInfo: " + updateVnoInfo
                + ", userInfo: " + userVO + ", ip: " + request.getRemoteAddr() + ")";
        logger.info(log);
        apiLogger(ApiLogVo.CATE_VNO, HttpServletResponse.SC_OK, tokenKey, userVO, updateVnoInfo.toString(), request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
        return new ApiCommonVo<AgentVO>(HttpServletResponse.SC_OK, "050", null, updatedAgentVO);
    }

    /**
     * 콜로그 조회
     *
     * @param userId      사용자 ID
     * @param tokenKey    토큰키
     * @param searchQuery 콜로그 검색 조건
     * @return 처리 정보(조회 성공여부, 콜로그 리스트 등)
     */
    public ApiCommonVo<ApiPaginationListVo<CallLogVO>> getCallLogList(String userId, String tokenKey, CallLogVO searchQuery) {
        long startTime = System.nanoTime();
        UserVO userVO = getUserInfoForToken(tokenKey);
        if (userVO == null || !userVO.getUserid().equals(userId)) {
            String log = "[API v1 콜로그 조회] 실패 - 토큰키가 유효하지 않거나 ID가 잘못됬습니다 ("
                    + "tokenKey: " + tokenKey + ", userId: " + userId
                    + ", searchQuery: " + searchQuery + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_CALLLOG, HttpServletResponse.SC_UNAUTHORIZED, tokenKey, null, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<ApiPaginationListVo<CallLogVO>>(HttpServletResponse.SC_UNAUTHORIZED, "CALLLOG", "토큰키가 유효하지 않거나 ID가 잘못됬습니다", null);
        }

        //1.데이터 타입 유효 검사
        try {
            validationUtil.validate(searchQuery);



            if (searchQuery.getPageSize() < 1 || searchQuery.getPageSize() > 100) {
                throw new InvalidDataException("페이징 번호가 1~100 사이가 아닙니다.", null);
            }
        } catch (InvalidDataException e) {
            long elapsedTime = (System.nanoTime() - startTime) / 1000000;

            String log = "[API v1 콜로그 조회] 실패 - 데이터 타입이 유효하지 않습니다.("
                    + "처리시간: " + elapsedTime + ", tokenKey: " + tokenKey
                    + ", userId: " + userId + ", searchQuery: " + searchQuery
                    + ", userInfo: " + userVO + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_CALLLOG, HttpServletResponse.SC_BAD_REQUEST, tokenKey, userVO, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<ApiPaginationListVo<CallLogVO>>(HttpServletResponse.SC_BAD_REQUEST, "CALLLOG", "데이터 타입이 유효하지 않습니다", null);
        }

        searchQuery.setUser_id(userVO.getUserid());

        /* 콜로그 조회 */
        Map<String, Object> map = supplyService.getCallLogListAPI(searchQuery);

        List<CallLogVO> callLogList = (List<CallLogVO>)map.get("list");

        System.out.println("-=----");
        System.out.println(callLogList);
        System.out.println("-=----");


        long elapsedTime = System.nanoTime() - startTime;

        String log = "[API v1 050 콜로그 조회] 성공("
                + "처리시간: " + elapsedTime + ", tokenKey: " + tokenKey
                + ", userId: " + userId + ", searchQuery: " + searchQuery
                + ", userInfo: " + userVO + ", ip: " + request.getRemoteAddr() + ")";
        logger.info(log);
        apiLogger(ApiLogVo.CATE_CALLLOG, HttpServletResponse.SC_OK, tokenKey, userVO, searchQuery.toString(), request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

        return new ApiCommonVo<ApiPaginationListVo<CallLogVO>>(HttpServletResponse.SC_OK, "CALLLOG", null, new ApiPaginationListVo<CallLogVO>(callLogList, searchQuery));
    }

    /**
     * 교환기 상태 조회
     *
     * @param tokenKey 토큰값
     * @return 처리 정보(교환기 상태)
     */
    public ApiCommonVo<Boolean> getIvrStatus(String tokenKey) {
        long startTime = System.nanoTime();
        UserVO userInfo = getUserInfoForToken(tokenKey);
        if (userInfo == null) {
            String log = "[API v1 교환기 상태 조회] 실패 - 토큰키가 유효하지 않습니다 ("
                    + "tokenKey: " + tokenKey + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_IVR_STAT, HttpServletResponse.SC_UNAUTHORIZED, tokenKey, null, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

            return new ApiCommonVo<Boolean>(HttpServletResponse.SC_UNAUTHORIZED, ApiLogVo.CATE_IVR_STAT, "토큰키가 유효하지 않습니다.", null);
        }

        Boolean ivrStatus = ivrService.getIvrStatus();

        long elapsedTime = System.nanoTime() - startTime;
        String log = "[API v1 교환기 상태 조회] 성공("
                + "처리시간: " + elapsedTime + ", tokenKey: " + tokenKey
                + ", userId: " + userInfo.getUserid() + ", ip: " + request.getRemoteAddr() + ")";
        logger.info(log);
        apiLogger(ApiLogVo.CATE_IVR_STAT, HttpServletResponse.SC_OK, tokenKey, null, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

        ApiCommonVo<Boolean> returnValue = new ApiCommonVo<Boolean>(HttpServletResponse.SC_OK, ApiLogVo.CATE_IVR_STAT, null, new Boolean(ivrStatus));
        return returnValue;
    }

    /* 로그 관련 부분 */
    public void apiLogger(String log_category, int log_statuscode, String log_tokenkey,
                          UserVO userVO, String log_event, String log_ipaddress, long log_elapsed_time) {
        ApiLogVo log = new ApiLogVo(log_category, log_statuscode, log_tokenkey, userVO != null ? userVO.getUserid() : null, log_event, log_ipaddress, log_elapsed_time);
        apiMainDao.insertApiLog(log);

    }

    public ApiCommonVo<Boolean> getSendMessageStatus(String tokenKey, MessageVO messageVO) {
        UserVO user = getUserInfoForMessageToken(tokenKey);
        ServiceMessagingVO user_ip = getUserIpForUserVO(user);
        long startTime = System.nanoTime();
        ApiCommonVo<Boolean> returnValue = null;
        if (user == null || !user.getUserid().equals(messageVO.getUserid())) {
            String log = "[API v2 메시지 전송] 실패 - 토큰키가 유효하지 않습니다 ("
                    + "tokenKey: " + tokenKey + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_SEND_MESSAGE, HttpServletResponse.SC_UNAUTHORIZED, tokenKey, user, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

            return new ApiCommonVo<Boolean>(HttpServletResponse.SC_UNAUTHORIZED, ApiLogVo.CATE_SEND_MESSAGE, "토큰키가 유효하지 않습니다.", null);
        }
        /*if (user_ip.getIp().equals(ipUtil.getPublicIP())) {*/

        Integer text = messageVO.getText().getBytes().length;
        Integer point = Integer.parseInt(user.getEpoint());

//			if(text < 80){
//				messageVO.setMsg_type("1");
//			}else if(text >= 80 && text <= 2000){
//				messageVO.setMsg_type("3");
//			}else{
//				String log = "[API v2 메시지 전송] 실패 - 글자수가 2,000byte를 초과하였습니다. ("
//						+ "tokenKey: " + tokenKey + ", ip: " + ipUtil.getPublicIP() + ")";
//				apiLogger(ApiLogVo.CATE_SEND_MESSAGE, HttpServletResponse.SC_RESET_CONTENT, tokenKey, user, log, request.getRemoteAddr(), (System.nanoTime()-startTime)/1000000);
//				return new ApiCommonVo<Boolean>(HttpServletResponse.SC_RESET_CONTENT, ApiLogVo.CATE_SEND_MESSAGE, "글자수가 2,000byte를 초과하였습니다.", null);
//			}

        if (text < 80 && point <= 22) {
            String log = "[API v2 메시지 전송] 실패 - 메시지 전송을 위한 포인트가 부족합니다. ("
                    + "tokenKey: " + tokenKey + ", ip: " + ipUtil.getPublicIP() + ")";
            apiLogger(ApiLogVo.CATE_SEND_MESSAGE, HttpServletResponse.SC_RESET_CONTENT, tokenKey, user, log, ipUtil.getPublicIP(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<Boolean>(HttpServletResponse.SC_RESET_CONTENT, ApiLogVo.CATE_SEND_MESSAGE, "메시지 전송을 위한 포인트가 부족합니다.", null);
        } else if (text >= 80 && point <= 55) {
            String log = "[API v2 메시지 전송] 실패 - 메시지 전송을 위한 포인트가 부족합니다. ("
                    + "tokenKey: " + tokenKey + ", ip: " + ipUtil.getPublicIP() + ")";
            apiLogger(ApiLogVo.CATE_SEND_MESSAGE, HttpServletResponse.SC_RESET_CONTENT, tokenKey, user, log, ipUtil.getPublicIP(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<Boolean>(HttpServletResponse.SC_RESET_CONTENT, ApiLogVo.CATE_SEND_MESSAGE, "메시지 전송을 위한 포인트가 부족합니다.", null);
        } else {

            messageService.sendSmsEpontCharge(messageVO);
            String log = "[API v2 메시지 전송] 성공 - 메시지가 성공적으로 전송되었습니다. ("
                    + "tokenKey: " + tokenKey + ", ip: " + ipUtil.getPublicIP() + ")";
            apiLogger(ApiLogVo.CATE_SEND_MESSAGE, HttpServletResponse.SC_OK, tokenKey, user, log, ipUtil.getPublicIP(), (System.nanoTime() - startTime) / 1000000);
            returnValue = new ApiCommonVo<Boolean>(HttpServletResponse.SC_OK, ApiLogVo.CATE_SEND_MESSAGE, messageVO.getText(), true);
        }
        /*} else {
            String log = "[API v2 메시지 전송] 실패 - 대량 메시징 가입 시 입력했던 IP와 맞지 않습니다. ("
                    + "tokenKey: " + tokenKey + ", ip: " + ipUtil.getPublicIP() + ")";
            apiLogger(ApiLogVo.CATE_SEND_MESSAGE, HttpServletResponse.SC_RESET_CONTENT, tokenKey, user, log, ipUtil.getPublicIP(), (System.nanoTime() - startTime) / 1000000);
            returnValue = new ApiCommonVo<Boolean>(HttpServletResponse.SC_RESET_CONTENT, ApiLogVo.CATE_SEND_MESSAGE, "대량 메시징 가입 시 입력했던 IP와 맞지 않습니다. 현재 IP : " + ipUtil.getPublicIP(), true);
        }*/
        return returnValue;
    }

    public ApiCommonVo<ApiPaginationListVo<CallLogVO>> getCallLogListSetTime(String userId, String tokenKey, CallLogVO searchQuery) {
        long startTime = System.nanoTime();
        UserVO userVO = getUserInfoForToken(tokenKey);
        if (userVO == null || !userVO.getUserid().equals(userId)) {
            String log = "[API v1 콜로그 조회] 실패 - 토큰키가 유효하지 않거나 ID가 잘못됬습니다 ("
                    + "tokenKey: " + tokenKey + ", userId: " + userId
                    + ", searchQuery: " + searchQuery + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_CALLLOG, HttpServletResponse.SC_UNAUTHORIZED, tokenKey, null, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<ApiPaginationListVo<CallLogVO>>(HttpServletResponse.SC_UNAUTHORIZED, "CALLLOG", "토큰키가 유효하지 않거나 ID가 잘못됬습니다", null);
        }

        //1.데이터 타입 유효 검사
        try {
            validationUtil.validate(searchQuery);

            if (searchQuery.getPageSize() < 1 || searchQuery.getPageSize() > 100) {
                throw new InvalidDataException("페이징 번호가 1~100 사이가 아닙니다.", null);
            }
        } catch (InvalidDataException e) {
            long elapsedTime = (System.nanoTime() - startTime) / 1000000;

            String log = "[API v1 콜로그 조회] 실패 - 데이터 타입이 유효하지 않습니다.("
                    + "처리시간: " + elapsedTime + ", tokenKey: " + tokenKey
                    + ", userId: " + userId + ", searchQuery: " + searchQuery
                    + ", userInfo: " + userVO + ", ip: " + request.getRemoteAddr() + ")";
            logger.info(log);
            apiLogger(ApiLogVo.CATE_CALLLOG, HttpServletResponse.SC_BAD_REQUEST, tokenKey, userVO, log, request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);
            return new ApiCommonVo<ApiPaginationListVo<CallLogVO>>(HttpServletResponse.SC_BAD_REQUEST, "CALLLOG", "데이터 타입이 유효하지 않습니다", null);
        }

        searchQuery.setUser_id(userVO.getUserid());

        /* 콜로그 조회 */
        List<CallLogVO> callLogList = supplyService.getCallLogListSetTime(searchQuery);


        long elapsedTime = System.nanoTime() - startTime;

        String log = "[API v1 050 콜로그 조회] 성공("
                + "처리시간: " + elapsedTime + ", tokenKey: " + tokenKey
                + ", userId: " + userId + ", searchQuery: " + searchQuery
                + ", userInfo: " + userVO + ", ip: " + request.getRemoteAddr() + ")";
        logger.info(log);
        apiLogger(ApiLogVo.CATE_CALLLOG, HttpServletResponse.SC_OK, tokenKey, userVO, searchQuery.toString(), request.getRemoteAddr(), (System.nanoTime() - startTime) / 1000000);

        return new ApiCommonVo<ApiPaginationListVo<CallLogVO>>(HttpServletResponse.SC_OK, "CALLLOG", null, new ApiPaginationListVo<CallLogVO>(callLogList, searchQuery));

    }
}
