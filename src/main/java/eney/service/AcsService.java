package eney.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import eney.domain.AcsTransmitVO;
import org.springframework.stereotype.Service;

import eney.domain.AcsTransmitVO;
import eney.mapper.AcsDao;


@Service
public class AcsService {

	@Resource
	AcsDao acsDao;
	/**
	 * 키사 연동정보에 맞게 DB에 packet 정보 insert
	 * @param acsVO
	 * @return
	 */
	public int SetTransmitPacket(AcsTransmitVO acsVO){
		int updateColumn = 0;
		acsDao.insertAcsTransmit(acsVO);
		acsVO.setReq_num(getLPad(acsVO.getReq_num(),12,'0'));
		
		acsDao.setAuthCodeManage(acsVO);
		acsDao.setCallbackManage(acsVO);
		acsDao.setCallbackManageHistory(acsVO);
		return updateColumn;
	}	

    /**
     * 전송 packet 가져오기
     * @param req_num
     * @return
     */
    public AcsTransmitVO GetTransmitPacket(String req_num) {
		return acsDao.getTransmitPacket(req_num);
		
	}
    
    /**
     * ARS인증 성공 시 상태 결과 업데이트
     * @param acsTransmitVO
     * @return
     */
    public int UpdateAcsStatus(AcsTransmitVO acsTransmitVO){
    	return acsDao.updateAcsStatus(acsTransmitVO);
    }
    
    /**
     * 발신번호 리스트 출력
     * @param acsTransmitVO
     * @return
     */
//    public List<AcsTransmitVO> getMessaingList(AcsTransmitVO acsTransmitVO){
//    	acsTransmitVO.setTotal_item_num(acsDao.getMessaingList(acsTransmitVO).size());
//    	return acsDao.getMessaingList(acsTransmitVO);
//    }
//
//	public List<AcsTransmitVO> getMessaingListByAcsVO(AcsTransmitVO acsTransmitVO){
//		acsTransmitVO.setTotal_item_num(acsDao.getMessaingListByAcsVO(acsTransmitVO).size());
//		return acsDao.getMessaingListByAcsVO(acsTransmitVO);
//	}


	public Map<String, Object> getMessaingListWithPage(AcsTransmitVO acsTransmitVO){

		acsTransmitVO.setTotalCount(acsDao.getMessaingListByAcsVO(acsTransmitVO).size());

		Map<String, Object> map = new HashMap<>();


		map.put("page", acsTransmitVO);
		map.put("list", acsDao.getMessaingListWithPage(acsTransmitVO));

		return map;
	}

	public Map<String, Object> getAllCidList(AcsTransmitVO acsTransmitVO){

		acsTransmitVO.setTotalCount(acsDao.getMessaingListByAcsVO(acsTransmitVO).size());

		Map<String, Object> map = new HashMap<>();


		map.put("list", acsDao.getAllCidList(acsTransmitVO));

		return map;
	}

    public List<AcsTransmitVO> getMessaingNumberList(){
		return acsDao.getMessaingNumberList();
	}
    
    /**
     * userid를 기준으로 발신번호 리스트 출력
     * @param userid
     * @return
     */
    public List<AcsTransmitVO> getMessaingListByUserid(String userid){
    	return acsDao.getMessaingListByUserid(userid);
    }
    
    /**
     * 발신번호 중복 확인
     * @param phone 휴대폰번호
     * @return
     */
    public AcsTransmitVO getMessagingDupleCheck(String phone){
    	return acsDao.getMessagingDupleCheck(phone);
    }
    
    /**
     * 오른쪽에 자릿수 채우기
     * @param str 텍스트 내용
     * @param size 텍스트 길이
     * @param c 채울 텍스트
     * @return
     */
    public String getRPad(String str, int size, char c) {
    	for(int i = (str.getBytes()).length; i < size; i++) {
    		str += c;
    	}
    	return str;
    }
    /**
     * 왼쪽에 자릿수 채우기
     * @param str 텍스트 내용
     * @param size 텍스트 길이
     * @param c 채울 텍스트
     * @return
     */
    public static String getLPad(String str, int size, char c) {
        for(int i = (str.getBytes()).length; i < size; i++) {
            str = c + str;
        }
        return str;
    }

	public void deleteMessagingNumber(AcsTransmitVO acsVO) {
		acsDao.deleteMessagingNumber(acsVO);
	}
	public void deleteMessagingNumberForUserid(AcsTransmitVO acsVO) {
		acsDao.deleteMessagingNumberForUserid(acsVO);
	}
	public AcsTransmitVO countNumber(AcsTransmitVO acsVO) {
		return acsDao.countNumber(acsVO);
	}
	
}
