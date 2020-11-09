package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.*;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class AnalyticsDao {
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	



	

	
	public List<LinkChannelVo> channel_idx_selector(int link_idx) {
		return  sqlSession.selectList("link.link_channel_idx_selector", link_idx);
	}


	public LinkChannelVo selectLinkIdxBycIdx(int link_channel_idx) {
		return  sqlSession.selectOne("link.selectLinkIdxBycIdx",link_channel_idx);
	}

	public LinkInfoVo selectLinkKeyBylinkIdx(Integer link_idx) {
		return sqlSession.selectOne("link.selectLinkKeyBylinkIdx",link_idx);
	}


    public List<Map<String, Object>> getAllCallAnalyticsById(String userId) {
		return sqlSession.selectList("analytics.getAllCallAnalyticsById",userId);
    }

    public List<Map<String, Object>> getCampaignCountSummaryById(HashMap hashMap) {
		return sqlSession.selectList("analytics.getCampaignCountSummaryById",hashMap);
    }

	public List<Map<String, Object>> getShortUrlCount(String shortUrl) {
		return sqlSession.selectList("analytics.getShortUrlCount",shortUrl);
	}

	public List<Map<String, Object>> getTitleListByUserId(String userId) {
		return sqlSession.selectList("analytics.getTitleListByUserId",userId);
	}

	public int getTotalCampaignCountById(String userId) {
		return sqlSession.selectOne("analytics.getTotalCampaignCountById",userId);
	}

	public List<BoardContentVO> getCampaignListByUserId(HashMap<String, Object> hashMap) {
		return sqlSession.selectList("analytics.getCampaignListByUserId",hashMap);
	}

    public List<ShortUrlJoinVO> getShortUrlListByIdx(String idx) {
		return sqlSession.selectList("analytics.getShortUrlListByIdx",idx);
    }

	public List<GAViewIdVO> getGAViewIdListByUserId(String userId) {
		return sqlSession.selectList("analytics.getGAViewIdListByUserId",userId);
	}

	//모든 아이디 리스트
	public List<GAViewIdVO> getGAViewIdList() {
		return sqlSession.selectList("analytics.getGAViewIdList");
	}

	public void insertGAKeywordData(List<GAKeyWordVO> gaKeyWordVOList) {
		sqlSession.insert("analytics.insertGAKeywordData",gaKeyWordVOList);
	}

	public void insertGAKeywordDataLog(GAKeyWordLogVO gaKeyWordLogVO) {
		sqlSession.insert("analytics.insertGAKeywordDataLog",gaKeyWordLogVO);
	}

	public int getAnalyticsViewIdByViewId(GAViewIdVO gaViewIdVO) {
		return sqlSession.selectOne("analytics.getAnalyticsViewIdByViewId", gaViewIdVO);
	}


	public int getAnalyticsViewIdCount(GAViewIdVO gaViewIdVO) {
		return sqlSession.selectOne("analytics.getAnalyticsViewIdCount", gaViewIdVO);
	}

	//아이디 기준
	public List<GAViewIdVO> getAnalyticsViewIdList(GAViewIdVO gaViewIdVO) {
		return sqlSession.selectList("analytics.getAnalyticsViewIdList",gaViewIdVO);
	}

	public int insertAnalyticsViewId(GAViewIdVO gaViewIdVO) {
		return sqlSession.insert("analytics.insertAnalyticsViewId",gaViewIdVO);
	}

	public int editAnalyticsViewId(GAViewIdVO gaViewIdVO) {
		return sqlSession.update("analytics.editAnalyticsViewId",gaViewIdVO);
	}

	public int deleteAnalyticsViewId(GAViewIdVO gaViewIdVO) {
		return sqlSession.delete("analytics.deleteAnalyticsViewId",gaViewIdVO);
	}

}