package eney.mapper;

import java.util.List;

import eney.DatasourceConfig;
import eney.domain.AcsTransmitVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AcsDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;

	public int insertAcsTransmit(AcsTransmitVO acsTransmitVO) {
		return sqlSession.insert("acs.insertAcsTransmit", acsTransmitVO);
	}

	public AcsTransmitVO getTransmitPacket(String req_num){
		return sqlSession.selectOne("acs.getTransmitPacket",req_num);
	}
	
	public int updateAcsStatus(AcsTransmitVO acsTransmitVO){
		return sqlSession.update("acs.updateAcsStatus",acsTransmitVO);
	}
	
	public List<AcsTransmitVO> getMessaingList(AcsTransmitVO acsTransmitVO){
		return sqlSession.selectList("acs.getMessaingList",acsTransmitVO);
	}
	public List<AcsTransmitVO> getMessaingNumberList(){
		return sqlSession.selectList("acs.getMessaingNumberList");
	}
	public List<AcsTransmitVO> getMessaingListByUserid(String userid){
		return sqlSession.selectList("acs.getMessaingListByUserid",userid);
	}
	public AcsTransmitVO getMessagingDupleCheck(String phone) {
		return sqlSession.selectOne("acs.getMessagingDupleCheck",phone);
	}
	public void setAuthCodeManage(AcsTransmitVO acsVO) {
		sqlSession.insert("acs.setAuthCodeManage",acsVO);
	}
	public void setCallbackManage(AcsTransmitVO acsVO){
		sqlSession.insert("acs.setCallbackManage",acsVO);
	}
	public void setCallbackManageHistory(AcsTransmitVO acsVO){
		sqlSession.insert("acs.setCallbackManageHistory",acsVO);
	}
	public void deleteMessagingNumber(AcsTransmitVO acsVO){
		sqlSession.delete("acs.deleteMessagingNumber",acsVO);
	}
	public void deleteMessagingNumberForUserid(AcsTransmitVO acsVO){
		sqlSession.delete("acs.deleteMessagingNumberForUserid",acsVO);
	}
	public AcsTransmitVO countNumber(AcsTransmitVO acsVO){
		return sqlSession.selectOne("acs.countNumber", acsVO);
	}

	public List<AcsTransmitVO> getMessaingListByAcsVO(AcsTransmitVO acsTransmitVO){
		return sqlSession.selectList("acs.getMessaingListByAcsVO",acsTransmitVO);
	}

	public List<AcsTransmitVO> getMessaingListWithPage(AcsTransmitVO acsTransmitVO){
		return sqlSession.selectList("acs.getMessaingListWithPage",acsTransmitVO);
	}
	public List<AcsTransmitVO> getAllCidList(AcsTransmitVO acsTransmitVO){
		return sqlSession.selectList("acs.getAllCidList",acsTransmitVO);
	}
	
}
