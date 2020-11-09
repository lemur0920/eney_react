package eney.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eney.DatasourceConfig;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.MessageVO;

@Repository
public class MessageDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;

	/**
	 * 메시지 전송
	 * @param msgVO
	 */
	public void pushMessage(MessageVO msgVO) {
		sqlSession.insert("message.pushMessage", msgVO);
	}

	/**
	 * 메시지 전송 결과 출력
	 * @param msgVO
	 * @return
	 */
	public MessageVO getMessageResult(MessageVO msgVO) {
		return (MessageVO) sqlSession.selectOne("message.getMessageResult", msgVO);
	}

    /**
     * Message-queue 검색
     *
     * @param msgVO dstaddr
     * @return
     */
    public MessageVO getMessageQueue(MessageVO msgVO) {
        return (MessageVO) sqlSession.selectOne("message.getMessageQueue", msgVO);
    }

    public List<Map<String, String>> getMessageList(Map<String, String> map) {
        return sqlSession.selectList("message.getMessageList", map);
    }

    public Integer getMessageTemplateListCnt(MessageTemplateVO messageTemplateVO) {
        return sqlSession.selectOne("service.getMessageTemplateListCnt", messageTemplateVO);
    }

    public MessageTemplateVO getMessageTemplateDetail(int idx) {
        return sqlSession.selectOne("service.getMessageTemplateDetail", idx);
    }

    public int deleteMessageTemplate(int idx) {
        return sqlSession.delete("service.deleteMessageTemplate", idx);
    }

    public List<MessageTemplateVO> getMessageTemplateList(MessageTemplateVO messageTemplateVO) {
        List<MessageTemplateVO> messageTemplateList = sqlSession.selectList("service.getMessageTemplateList", messageTemplateVO);
        return messageTemplateList;
    }

    public void insertMessageTemplateList(MessageTemplateVO messageTemplateVO) {
        sqlSession.insert("service.insertMessageTemplateList", messageTemplateVO);
    }

    public int updateMessageTemplateList(MessageTemplateVO messageTemplateVO) {
        return sqlSession.update("service.updateMessageTemplateList", messageTemplateVO);
    }


    public Integer getMessageResultListCnt(Map<String, Object> map) {
        return sqlSession.selectOne("service.getMessageResultListCnt", map);
    }

    public List<MsgResultVO> getMessageResultList(Map<String, Object> map) {
        List<MsgResultVO> messageTemplateList = sqlSession.selectList("service.getMessageResultList", map);
        return messageTemplateList;
    }
    public List<Map> getMsgResultTableList() {
        List<Map> messageTemplateList = sqlSession.selectList("service.getMsgResultTableList");
        return messageTemplateList;
    }


    public Integer getBlockNumberListCnt(BlockNumberVO blockNumberVO) {
        return sqlSession.selectOne("service.getBlockNumberListCnt", blockNumberVO);
    }

    public List<BlockNumberVO> getBlockNumberList(BlockNumberVO blockNumberVO) {
        List<BlockNumberVO> blockNumberVOS = sqlSession.selectList("service.getBlockNumberList", blockNumberVO);
        return blockNumberVOS;
    }

    public int webSendSMS(WebSmsSendVO webSmsSendVO) {
        return sqlSession.selectOne("service.webSendSMS", webSmsSendVO);
    }

    public void sendSmsEpointCharge(MessageVO msgVO) {
        sqlSession.update("message.sendSmsEpointCharge", msgVO);
    }

}
