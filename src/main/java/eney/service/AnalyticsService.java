package eney.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import eney.domain.*;
import eney.domain.*;
import org.springframework.stereotype.Service;

import eney.mapper.AnalyticsDao;
import eney.mapper.LinkDao;

@Service
public class AnalyticsService {

	@Resource
	AnalyticsDao analyticsDao;

	@Resource
	LinkService linkService;

	@Resource
	LinkDao linkDao;

	@Resource
	AnalyticsService analyticService;




//	/**
//	 * 중복 체크
//	 *
//	 * @param tag
//	 * @return
//	 */
//	public boolean checkUserTag(LinkTagVo tag) {
//
//
//		List<LinkTagVo> check = analyticsDao.checkUserTag(tag);
//
//		if (check != null)
//
//			return false;
//		else
//
//			return true;
//	}

//	/**
//	 * TODO link_idx값을 기준으로 link_channel_idx를 리턴해야함, link_channel_idx는 값이 여러개니, 배열로 받아서 향상된 포문으로 리턴
//	 *
//	 * @param link_idx
//	 * @param tag_idx
//	 * @return linkManagement.getManagement_idx()
//	 */
//	public List<LinkManagementVo> channel_idx_selector(int link_idx, int tag_idx) {
//		List<LinkChannelVo> channels = (List<LinkChannelVo>) analyticsDao.channel_idx_selector(link_idx);
//		LinkManagementVo linkManagement = new LinkManagementVo();
//
//		for (LinkChannelVo vo : channels) {
//
//			int channel_idx = vo.getLink_channel_idx();
//			linkManagement.setManagement_tag_idx(tag_idx);
//			linkManagement.setManagement_channel_idx(channel_idx);
//			analyticsDao.insertlinkManagement(linkManagement);
//
//		}
//
//		return analyticsDao.getManagement_channel_idx(linkManagement.getManagement_tag_idx());
//	}
//
	
	
/*	public int ad2dtag(LinkTagVo addtag, String [] channels, UserVO user,String targetUrl){
		
		addtag.setTag_userid(user.getUserid());
		
		LinkInfoVo linkInfo = linkService.addLink(targetUrl, linkService.keyGen(), LinkService.LINK_CATEORY_ANALYTICS, LinkInfoVo.LINK_TYPE_LINK, user);

			if(1 != analyticsDao.insertlinkTag(addtag)){
		
			}
			
		for(String channer: channels){
		
			linkService.makeLinkChannel(linkInfo.getLink_idx(), channer, LinkService.LINK_CATEORY_ANALYTICS, addtag.getTag_idx(), user.getUserid());
		}
		return addtag.getTag_idx();
	}*/

//
//	public List<LinkTagVo> getTagListByUserid(String userId) {
//		return analyticsDao.selectTagListByUserid(userId);
//	}
//
//	public LinkTagVo getTagByTagIdx(Integer tagIdx) {
//		return analyticsDao.selectTagByTagIdx(tagIdx);
//	}


	public LinkChannelVo linkIdxSelector(int management_channel_idx) {

		return analyticsDao.selectLinkIdxBycIdx(management_channel_idx);

	}

	public LinkInfoVo linkKeySelector(Integer link_idx) {

		return analyticsDao.selectLinkKeyBylinkIdx(link_idx);

	}


	public List<Map<String, Object>> getAllCallAnalyticsById(String userid) {


//		if(!list.isEmpty()){
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).get("total"));
//			System.out.println(list.get(i).get("connected"));
//			System.out.println(list.get(i).get("agent_not_found"));
//			System.out.println(list.get(i).get("connect_failed"));
//			}
//		}
		return analyticsDao.getAllCallAnalyticsById(userid);
	}

	public Map<String,Map<String, Integer>> getCampaignCountSummaryById(String userId,String idx) {

		HashMap<String,Object> hashMap = new HashMap<>();
		hashMap.put("id",userId);
		hashMap.put("idx",idx);

		List<Map<String, Object>> shortUrlList = analyticsDao.getCampaignCountSummaryById(hashMap); //아이디가 가진 shorturl 총 갯수
		Map<String,Map<String, Integer>> shortUrlAnalytics= new TreeMap<>(); // 키 값 채널명 리스트(날짜 , 카운트)
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


		for (int i = 0; i < shortUrlList.size(); i++) {

			List<Map<String, Object>> mapList = analyticsDao.getShortUrlCount((String) shortUrlList.get(i).get("shortUrl"));

			if (mapList.isEmpty())
				continue;

			String channel = (String) shortUrlList.get(i).get("channel");

			if (shortUrlAnalytics.containsKey(channel)) {

				Map<String, Integer> analyticsList = shortUrlAnalytics.get(channel);

				for (int j = 0; j < mapList.size(); j++) {
					String date = (String) mapList.get(j).get("date"); // 날짜
					String count = String.valueOf(mapList.get(j).get("count")); // 입장 횟수

					if(analyticsList.containsKey(date)){
						Integer num = analyticsList.get(date);
						analyticsList.put(date,num+Integer.parseInt(count));
					}else
						analyticsList.put(date,Integer.parseInt(count));

					}
					shortUrlAnalytics.put(channel,analyticsList);
				} else {

					Map<String, Integer> treeMap = new TreeMap<>();
					for (int k = 0; k <7 ; k++) {
							String formatted = format.format(getDate(k));
							treeMap.put(formatted,0);
					}
					for (int j = 0; j < mapList.size(); j++) {
						String date = (String) mapList.get(j).get("date"); // 날짜
						String count = String.valueOf(mapList.get(j).get("count")); // 입장 횟수
						treeMap.put(date, Integer.parseInt(count));
					}
					shortUrlAnalytics.put(channel, treeMap);
				}
		}
		return shortUrlAnalytics;
	}

	public List<Map<String, Object>> getTitleListByUserId(String userId){
		return analyticsDao.getTitleListByUserId(userId);
	}


	public ListVO getCampaignPagingByUserId(String userId, String nowPage) {

		Pagination pagination;
		int totalCount = analyticsDao.getTotalCampaignCountById(userId);

		if (nowPage == null) {
			pagination = new Pagination(totalCount);

		} else {
			pagination = new Pagination(totalCount, Integer.parseInt(nowPage));
		}

		pagination.setPage_per_item_num(5); // 페이지 당 캠페인 5개 나올 수 있게 제작
		HashMap<String,Object> hashMap = new HashMap<>();

		hashMap.put("id",userId);
		hashMap.put("startNum",pagination.getPresent_first_item_idx());
		hashMap.put("endNum",pagination.getEndRowNumber());

		List<BoardContentVO> list = analyticsDao.getCampaignListByUserId(hashMap);

		ListVO listVO = new ListVO(list,pagination);

		return listVO;
	}

	public List<ShortUrlJoinVO> getShortUrlListByIdx(String idx) {
		return analyticsDao.getShortUrlListByIdx(idx);
	}

	private Date getDate(int preDay){
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -preDay);
		return cal.getTime();
	} //원하는 날짜 구하는 함수


    public Map<String, Object> getAnalyticsViewIdList(GAViewIdVO gaViewIdVO) {

        gaViewIdVO.setTotalCount(analyticsDao.getAnalyticsViewIdCount(gaViewIdVO));

        Map<String, Object> map = new HashMap<>();

        List<GAViewIdVO> viewIdList = analyticsDao.getAnalyticsViewIdList(gaViewIdVO);

        map.put("list", viewIdList);
        map.put("page", gaViewIdVO);

        return map;
    }

    public int insertAnalyticsViewId(GAViewIdVO gaViewIdVO) {
		int check = analyticsDao.getAnalyticsViewIdByViewId(gaViewIdVO);

		if(check > 0){
			return 0;
		}else{
			return analyticsDao.insertAnalyticsViewId(gaViewIdVO);
		}
    }

    public int editAnalyticsViewId(GAViewIdVO gaViewIdVO) {
        return analyticsDao.editAnalyticsViewId(gaViewIdVO);
    }

    public int deleteAnalyticsViewId(GAViewIdVO gaViewIdVO) {
        return analyticsDao.deleteAnalyticsViewId(gaViewIdVO);
    }


}
