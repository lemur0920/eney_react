package eney.mapper;

import java.util.List;
import java.util.Map;

import eney.DatasourceConfig;
import eney.domain.LinkChannelVo;
import eney.domain.LinkInfoVo;
import eney.domain.LinkLogVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class LinkDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * 단축 링크 생성
	 * @param insertLinkData 생성할 단축링크 정보
	 * @return
	 */
	public Integer insertLinkInfo(LinkInfoVo insertLinkData){
		return sqlSession.insert("link.insertLinkInfo", insertLinkData);
	}
	
	/**
	 * 단축 링크 수정
	 * @param updateQuery 수정할 내용
	 * @return
	 */
	public Integer updateLinkInfo(LinkInfoVo updateQuery){
		return sqlSession.update("link.updateLinkInfo", updateQuery);
	}
	
	/**
	 * 링크 정보 조회
	 * @param link_idx 링크 정보 IDX
	 * @return
	 */
	public LinkInfoVo selectLinkInfo(Integer link_idx){
		LinkInfoVo selectQuery = new LinkInfoVo();
		selectQuery.setLink_idx(link_idx);
		return selectLinkInfo(selectQuery);
	}
	
	/**
	 * 링크 정보 조회
	 * @param link_idx 링크 정보 IDX
	 * @return
	 */
	public LinkInfoVo selectLinkInfo(LinkInfoVo selectQuery){
		return (LinkInfoVo) sqlSession.selectOne("link.selectLinkInfo", selectQuery);
	}
	
	/**
	 * 링크 URL 조회
	 * @param linkInfo 조회할 링크 정보
	 * @return 연결대상 URL
	 */
	public Map<String, Object> selectLinkSimpleInfo(Map<String, Object> selectQuery){
		return sqlSession.selectOne("link.selectLinkSimpleInfo", selectQuery);
	}
	
	/* 링크 채널 부분 */
	/**
	 * 링크 채널 조회
	 * @param selectQuery 조회 쿼리
	 * @return
	 */
	public LinkChannelVo selectLinkChannel(LinkChannelVo selectQuery){
		return (LinkChannelVo) sqlSession.selectOne("link.selectLinkChannel", selectQuery);
	}
	
	/**
	 * 링크 채널 리스트 조회
	 * @param selectQuery 조회 쿼리
	 * @return
	 */
	public List<LinkChannelVo> selectLinkChannelList(LinkChannelVo selectQuery){
		return sqlSession.selectList("link.selectLinkChannel", selectQuery);
	}
	
	/**
	 * 링크 채널 등록
	 * @param insertData 추가할 링크 채널
	 * @return
	 */
	public Integer insertLinkChannel(LinkChannelVo insertData){
		return sqlSession.insert("link.insertLinkChannel", insertData);
	}
	
	/**
	 * 링크 접속 로그 추가
	 * @param insertLogData 추가할 접속 로그
	 * @return
	 */
	public Integer insertLinkLog(LinkLogVo insertLogData){
		return sqlSession.insert("link.insertLinkLog", insertLogData);
	}

	public Map<String, Object> getLinkChannel(Map<String, Object> selectQuery){
		return sqlSession.selectOne("link.getLinkChannel", selectQuery);
	}
	
	
}
