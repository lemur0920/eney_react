package eney.mapper;

import java.util.List;
import java.util.Map;

import eney.DatasourceConfig;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.EpointLogVo;
import eney.domain.G_PayVO;
import eney.domain.ItemVo;
import eney.domain.PaymentLogVo;
import eney.domain.PaymentVO;

@Repository
public class PaymentDao{

	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	
	public PaymentVO getPaymentVO(PaymentVO payVO) {
		return (PaymentVO)sqlSession.selectOne("pay.getPaymentVO", payVO);
	}
	
	/**
	 * 서비스 코드로 결제내역 검색
	 * @param payVO
	 */
	public ItemVo getItemByService_code(String service_code){
		return sqlSession.selectOne("pay.getItemByService_code", service_code);
	}
	
	public int insertPaymentLog(PaymentLogVo paymentLog) {
		return sqlSession.insert("pay.insertPaymentLog", paymentLog);
	}
	
	public int updatePaymentLog(PaymentLogVo paymentLog) {
		return sqlSession.update("pay.updatePaymentLog2", paymentLog);
	}
	
	
	public PaymentLogVo getPaymentLogByOrderid(Integer orderid){
		return sqlSession.selectOne("pay.getPaymentLogByOrderid", orderid);
	}
	
	/**
	 * 050 사용기간 연장
	 * @param extendData 
	 */
	public int extendVnoAgent(Map<String, Object> extendData) {
		return sqlSession.update("pay.extendVnoAgent",extendData);
	}
	
	/**
	 * epoint 충전
	 * @param chargeEpointData
	 */
	public int chargeEpoint(Map<String, Object> chargeEpointData) {
		return sqlSession.update("pay.chargeEpoint", chargeEpointData);
	}

	/**
	 * 결제내역 로그 등록
	 * @param gpayVO
	 */
	public void submitPaymentLog(G_PayVO gpayVO) {
		sqlSession.insert("pay.submitPaymentLog", gpayVO);
	}

	/**
	 * 결제 아이디를 가지고 결제내역 출력
	 * @param gPayVO order_id
	 * @return
	 */
	public G_PayVO getGPaymentVO(G_PayVO gPayVO) {
		return (G_PayVO)sqlSession.selectOne("pay.getGPaymentVO", gPayVO);
	}
	/**
	 * 결제 상태와 메시지 업데이트
	 * @param gpayVO
	 */
	public void updatePaymentLog(G_PayVO gpayVO) {
		sqlSession.update("pay.updatePaymentLog", gpayVO);
		//return getGPaymentVO(gpayVO);
	}

	/**
	 * 만료일자 수정
	 * @param gpayVO
	 */
	public void updateAgent(G_PayVO gpayVO) {
		sqlSession.update("pay.updateAgent",gpayVO);
	}

	
	public void extend050Agent(G_PayVO gpayVO) {
		sqlSession.update("pay.extend050Agent",gpayVO);
	}

	public void chargeEpoint(G_PayVO gpayVO) {
		
	}

	public String getAdUploaderId(int idx) {
		return (String)sqlSession.selectOne("pay.getAdUploaderId",idx);
	}
	
	/**
	 * 이포인트 로그 추가 (n)
	 * @param epointLog
	 */
	public void insertEpointLog(EpointLogVo epointLog){
		sqlSession.insert("pay.insertEpointLog", epointLog);
	}

	

	public void insertEpointChargeLog(PaymentVO payVO) {
		sqlSession.insert("pay.insertEpointChargeLog", payVO);
	}
	
	public int getEpointLogCnt(EpointLogVo epointLogVo) {
		return (Integer)sqlSession.selectOne("pay.getEpointLogCnt",epointLogVo);
	}

	/**
	 * epoint 사용내역 리스트 출력
	 * @param paymentVO userid
	 * @return epoint 사용내역 리스트
	 */
	public List<EpointLogVo> getEpointLogs(EpointLogVo epointLogVo) {
		return sqlSession.selectList("pay.getEpointLogs",epointLogVo);
	}

	/**
	 * 사용자 별 결제내역 개수
	 * @param paymentVO userid, item_cate, status
	 */
	public int getPaymentLogCnt(PaymentVO paymentVO) {
		return (Integer)sqlSession.selectOne("pay.getPaymentLogCnt",paymentVO);
	}

	/**
	 * 결제내역 리스트 출력
	 * @param paymentVO
	 * @return 결제내역 리스트
	 */
	public List<PaymentVO> getPaymentLogs(PaymentVO paymentVO) {
		return sqlSession.selectList("pay.getPaymentLogs",paymentVO);
	}

	/**
	 * 이벤트 등록 아이디 검색
	 * @param ad_request_idx
	 */
	public String getAdBuyerId(int ad_request_idx) {
		return (String)sqlSession.selectOne("pay.getAdBuyerId", ad_request_idx);
	}

}
