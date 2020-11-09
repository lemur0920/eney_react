package eney.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.LinkChannelVo;
import eney.domain.LinkInfoVo;
import eney.domain.LinkLogVo;
import eney.domain.UserVO;
import eney.util.Base62Util;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eney.mapper.LinkDao;

@Service
public class LinkService {
	
	public static final String SERVER_DOMAIN = "http://www.eney.co.kr";
	public static final String LINK_PATH = "/link";

	private static final Logger logger = LoggerFactory.getLogger(LinkService.class);
	
	@Resource
	HttpServletRequest request;
	@Resource
	LinkDao linkDao;
	
	/**
	 * 단축 링크 연결
	 * @param linkId 링크 IDX (BASE62 인코딩)
	 * @param linkKey 링크 KEY (없을경우 NULL 입력)
	 * @param linkChannel 유입 채널 유형 IDX (BASE62 인코딩)
	 * @param request
	 * @return 실제로 연결되는 URL
	 */
	public String linkContact(String linkId, String linkKey, String linkChannel, HttpServletRequest request){
		/* 링크 정보를 읽음*/
		Map<String, Object> selectQuery = new HashMap<>();
		selectQuery.put("link_idx", Base62Util.decode(linkId).intValue());
		selectQuery.put("link_key", linkKey);
		if(linkChannel != null && linkChannel.equals("") == false){
			selectQuery.put("linkChannel", Base62Util.decode(linkChannel).intValue());
		}
		
		Map<String, Object> linkSimpleInfo = linkDao.selectLinkSimpleInfo(selectQuery);
		
		if(linkSimpleInfo != null && linkSimpleInfo.containsKey("link_idx")){
			/* 링크 접속 로그 생성 */
			LinkLogVo addLogData = new LinkLogVo();
			addLogData.setLink_idx((Integer) linkSimpleInfo.get("link_idx"));
			addLogData.setLink_log_ip(request.getRemoteAddr());
			addLogData.setLink_log_agent(request.getHeader("user-agent"));
			addLogData.setLink_channel_idx((Integer) linkSimpleInfo.get("link_channel_idx"));

			String referer = request.getHeader("Referer");
			if(referer != null){
				try {
					URL url = new URL(referer);
					/*if(url.getHost().equals("eney.co.kr") == false
							&& url.getHost().equals("www.eney.co.kr") == false){*/
						addLogData.setLink_log_referrer_url(referer);
						addLogData.setLink_log_referrer_host(url.getHost());
						
						String query = url.getQuery();
						if(query != null){
							try{
								Matcher qMatch = Pattern.compile("[\\?&]q=([^&]+)").matcher(query);
								Matcher queryMatch = Pattern.compile("[\\?]&query=([^&]+)").matcher(query);
								Matcher qbMatch = Pattern.compile("[\\?&]qb=([^&]+)").matcher(query);
								if(qMatch.find()){
									addLogData.setLink_log_referrer_search_query(URLDecoder.decode(qMatch.group().replaceFirst("q=", ""), "UTF-8"));
								} else if(queryMatch.find()) {
									addLogData.setLink_log_referrer_search_query(URLDecoder.decode(queryMatch.group().replaceFirst("query=", ""), "UTF-8"));
								} else if(qbMatch.find()){
									/* 네이버 지식인에서 유입된 경우 base64로 인코딩된 qb 파라미터값을 디코딩해서 검색어로 넣어줌 */
									addLogData.setLink_log_referrer_search_query(new String(Base64.decodeBase64(qbMatch.group().replaceFirst("qb=", ""))));
								}
							} catch (UnsupportedEncodingException e) {
								/* 파라메터값을 변환을 못하면 무시하고 진행해도 되므로 별도의 예외처리를 하지 않음 */
							}
						}
				/*	} else {
						 내부에서 요청한 내용을 기록을 남기지 않고 넘겨줌 
						return (String) linkSimpleInfo.get("link_url");
					}*/
				} catch (MalformedURLException e) {
					/* URL 형식이 아닌 경우 무시하기때문에 별도의 예외 처리를 하지 않음 */
				} 
			}
			/* 링크 접속 로그 생성 */
			linkDao.insertLinkLog(addLogData);
			return (String) linkSimpleInfo.get("link_url");
		} else {
			/* 링크  */
			return null;
		}
	}
	
	/**
	 * 링크 검색
	 * @param selectQuery 링크 검색 조건
	 * @return
	 */
	public LinkInfoVo selectLinkInfo(LinkInfoVo selectQuery){
		return linkDao.selectLinkInfo(selectQuery);
	}
	
	/**
	 * 링크 검색
	 * @param link_idx 링크 IDX
	 * @return
	 */
	public LinkInfoVo selectLinkInfo(Integer link_idx){
		return linkDao.selectLinkInfo(link_idx);
	}
	
	
	/**
	 * 링크 삭제
	 * @param link_idx
	 * @return
	 */
	public Integer deleteLinkInfo(Integer link_idx){
		LinkInfoVo updateQuery = new LinkInfoVo();
		updateQuery.setLink_idx(link_idx);
		updateQuery.setLink_delete(true);
		
		return linkDao.updateLinkInfo(updateQuery);
	}
	
	/**
	 * 단축 링크 정보 입력
	 * @param link_url 원래 URL
	 * @param link_key 단축 링크를 사용하기 위한 KEY (null가능)
	 * @param link_category 단축링크 카테고리, adpower_category 참조 (null 가능)
	 * @param link_type 링크 유형 (링크, 이미지등..)
	 * @param userInfo 사용자 정보
	 * @return 단축 링크 정보
	 */
	public LinkInfoVo addLink(String link_url, String link_key, 
			String link_category, Integer link_type, UserVO userInfo){
		LinkInfoVo addLinkInfo = new LinkInfoVo();
		addLinkInfo.setLink_url(link_url);
		addLinkInfo.setLink_key(link_key);
		addLinkInfo.setLink_category(link_category);
		addLinkInfo.setLink_type(link_type);
		addLinkInfo.setLink_userid(userInfo.getUserid());
		
		linkDao.insertLinkInfo(addLinkInfo);
		
		logger.info("[단축 URL 생성] 성공("
				+ "addLinkInfo: " + addLinkInfo
				+ ", userInfo: " + userInfo
				+ ", ip: " + request.getRemoteAddr() + ")");
		return addLinkInfo;
	}
	
	/**
	 * 단축 링크 생성
	 * @param link_url 원래 URL
	 * @param link_key 단축 링크를 사용하기 위한 KEY (null가능)
	 * @param link_category 단축링크 카테고리, adpower_category 참조 (null 가능)
	 * @param link_type 링크 유형 (링크, 이미지등..)
	 * @param userInfo 사용자 정보
	 * @return 생성된 단축 링크 주소
	 */
	public String makeLink(String link_url, String link_key, 
			String link_category, Integer link_type, UserVO userInfo){
		LinkInfoVo addLinkInfo = addLink(link_url, link_key, link_category, link_type, userInfo);
		return linkUrl(addLinkInfo.getLink_idx(), addLinkInfo.getLink_key(), null);
	}
	
	/**
	 * 링크 채널 생성
	 * @param link_idx 링크 IDX
	 * @param link_channel_name 링크 채널 이름
	 * @param link_channel_userid 채널을 생성한 사용자의 ID
	 * @return 채널이 적용된 URL값
	 */
	public String makeLinkChannel(Integer link_idx, String link_channel_name,
				String link_channel_userid){
		return makeLinkChannel(link_idx, link_channel_name, LinkChannelVo.LINK_CHANNEL_REF_CATEGORY_USER_MAKE
								, null, link_channel_userid);
	}
	
	
	/**
	 * 링크 채널 생성
	 * @param link_idx 링크 IDX
	 * @param link_channel_name 링크 채널 이름
	 * @param link_channel_ref_category 링크를 생성한 메뉴
	 * @param link_channel_ref_idx 링크를 생성한 메뉴의 IDX
	 * @param link_channel_userid 채널을 생성한 사용자의 ID
	 * @return 채널이 적용된 URL값
	 */
	public String makeLinkChannel(Integer link_idx, String link_channel_name,
				String link_channel_ref_category, Integer link_channel_ref_idx, 
				String link_channel_userid){
		LinkInfoVo linkInfoData = linkDao.selectLinkInfo(link_idx);
		if(linkInfoData == null || linkInfoData.getLink_idx() == null){
			return null;
		}
		
		LinkChannelVo channelQuery = new LinkChannelVo();
		channelQuery.setLink_idx(link_idx);
		channelQuery.setLink_channel_name(link_channel_name);
		channelQuery.setLink_channel_ref_category(link_channel_ref_category);
		channelQuery.setLink_channel_ref_idx(link_channel_ref_idx);
		channelQuery.setLink_channel_userid(link_channel_userid);
	
		LinkChannelVo linkChannelSelectData = linkDao.selectLinkChannel(channelQuery);
	
		if(linkChannelSelectData == null || linkChannelSelectData.getLink_idx() == null){
			/* 등록되어 있는 채널이 없는경우 */
			linkDao.insertLinkChannel(channelQuery);
			logger.info("[단축 URL 채널 생성] 성공("
					+ "channelQuery: " + channelQuery
					+ ", linkInfoData: " + linkInfoData
					+ ", userId: " + link_channel_userid
					+ ", ip: " + request.getRemoteAddr() + ")");
			
			return linkUrl(linkInfoData.getLink_idx(), linkInfoData.getLink_key(), channelQuery.getLink_channel_idx()); 
		} else {
			/* 이미 채널이 등록 되있는 경우 */
			return linkUrl(linkInfoData.getLink_idx(), linkInfoData.getLink_key(), linkChannelSelectData.getLink_channel_idx()); 
		}
	}
	
	/**
	 * 생성된 링크 채널 리스트 조회 (파라메터에 null입력시 해당 조건을 체크하지 않음)
	 * @param link_idx 링크 idx
	 * @param link_channel_ref_category 링크를 생성한 메뉴
	 * @param link_channel_ref_idx 링크를 생성한 메뉴의 IDX
	 * @param link_channel_userid 채널을 생성한 사용자의 ID
	 * @return 링크 채널 리스트
	 */
	public List<LinkChannelVo> selectLinkChannel(Integer link_idx,
			String link_channel_ref_category, Integer link_channel_ref_idx, 
			String link_channel_userid ){
		LinkChannelVo searchChannerQuery = new LinkChannelVo();
		searchChannerQuery.setLink_idx(link_idx);
		searchChannerQuery.setLink_channel_ref_category(link_channel_ref_category);
		searchChannerQuery.setLink_channel_ref_idx(link_channel_ref_idx);
		searchChannerQuery.setLink_channel_userid(link_channel_userid);
		
		return linkDao.selectLinkChannelList(searchChannerQuery);	
	}
	
	/**
	 * Link 데이터를 이용하여 URL 링크 출력
	 * @param linkIdx 링크 IDX (필수)
	 * @param linkKey 링크 접속에 필요한 키 (선택)
	 * @param linkChannelIdx 링크 채널 IDX (선택)
	 * @return
	 */
	public String linkUrl(Integer linkIdx, String linkKey, Integer linkChannelIdx){
		String url = SERVER_DOMAIN + LINK_PATH + "/" + Base62Util.encode(linkIdx);
		if(linkKey == null){
			if(linkChannelIdx != null){
				url += "/0";
			}
		} else {
			url += "/" + linkKey;
		}
		if(linkChannelIdx != null){
			url += "/" + Base62Util.encode(linkChannelIdx);
		}

		return url;
	}
	
	/**
	 * Link Key 생성 (난수 발생)
	 * @return
	 */
	public String keyGen(){
		Random random = new Random();
		return Base62Util.encode(random.nextInt(Integer.MAX_VALUE));
	}

}