package eney.mapper;

import java.util.List;
import java.util.Map;

import eney.DatasourceConfig;
import eney.domain.AlertVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.AlertVO;

@Repository
public class AlertDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * alert 리스트 출력
	 * @param param 받는사람 아이디, check_yn
	 * @return 해당 아이디의 alert 리스트 출력
	 */
	public List<AlertVO> getReloadData(AlertVO param) {
		return sqlSession.selectList("alert.getReloadData", param);
	}

	/**
	 * alert_code를 기준으로 userid, 번호 수, 종료 일자 출력
	 * @param param alert_code
	 * @return userid, 번호 개수, 종료일자
	 */
	public List<Map<String, Object>> getExpireVnoUserList(AlertVO param) {
		return sqlSession.selectList("alert.getExpireVnoUserList", param);
	}
	
	public List<Map<String, Object>> getExpireVnoUserListSejong(AlertVO param) {
		return sqlSession.selectList("alert.getExpireVnoUserListSejong", param);
	}
	
	
	public List<Map<String, Object>> getExpireServicePatchcallList(AlertVO param) {
		return sqlSession.selectList("alert.getExpireServicePatchcallList", param);
	}
	public List<Map<String, Object>> getExpireServiceWebList(AlertVO param) {
		return sqlSession.selectList("alert.getExpireServiceWebList", param);
	}
	
	/**
	 * DB에 알람 등록
	 * @param alertVO
	 */
	public void pushAlertExpireVno(AlertVO alertVO) {
		/* 아이템 코드에 따른 알람 리스트 출력 */
		AlertVO item_info = (AlertVO)sqlSession.selectOne("alert.getAlertItemInfo", alertVO);
		alertVO.setMenu_name(item_info.getItem_menu_name());
		
		if(item_info.getItem_type().equals("system")){
			alertVO.setPusher_id("system");
			alertVO.setType("system");
		}
		if(alertVO.getDescription()==null)
			alertVO.setDescription(item_info.getDescription());
		
		sqlSession.insert("alert.pushAlertExpireVno", alertVO);
	}
	/**
	 * 알람 전송
	 */
	public Integer insertAlert(AlertVO alertVO){
		return sqlSession.insert("alert.insertAlert", alertVO);
	}
	/**
	 * alert_id를 가지고 알람 정보 출력
	 * @param alert_id
	 * @return 알람 정보
	 */
	public AlertVO getAlertVOById(int alert_id) {
		return (AlertVO) sqlSession.selectOne("alert.getAlertVOById", alert_id);
	}
	/**
	 * alert_id와 puller_id를 통하여 check_yn 업데이트
	 * @param param - alert_id, puller_id, check_yn
	 */
	public void updateAlert(AlertVO param) {
		sqlSession.update("alert.updateAlert", param);
	}

	public List<Map<String, Object>> getExpireServiceRecordList(AlertVO param) {
		return sqlSession.selectList("alert.getExpireServiceRecordList", param);
	}
	
	public List<Map<String, Object>> getExpireServiceCallbackList(AlertVO param) {
		return sqlSession.selectList("alert.getExpireServiceCallbackList", param);
	}

}
